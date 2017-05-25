package co.develoop.androidcleanarchitecture.screen.presenter;

import android.support.design.widget.Snackbar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding2.support.design.widget.RxSnackbar;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;
import co.develoop.androidcleanarchitecture.screen.view.recycler.EndlessRecyclerViewScrollObservable;
import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewDiffUtilCallback;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class InfiniteAdapterPresenter<V extends InfiniteAdapterPresenterView, T extends AdapterItem> extends Presenter<V> {

    private List<T> mList;

    @Override
    protected void init() {
        mList = new LinkedList<>();

        bindEndlessRecyclerViewScrollObservable();

        addSubscription(RxSnackbar.dismisses(getSnackBar())
                .flatMap(calculateRecyclerViewDiffsWithLoading())
                .doOnNext(showResults())
                .flatMap(loadMoreData())
                .flatMap(calculateRecyclerViewDiffs())
                .subscribe(showResults()));

        loadInitialData();
    }

    public List<T> getListData() {
        return mList;
    }

    public abstract Observable<Transaction<List<T>>> getLoadObservable();

    public abstract Observable<Object> getPaginationObservable(int page);

    public abstract Completable getItemClickCompletable();

    public abstract T getLoadingObject();

    public abstract T getFooterObject();

    public void bindItemClick(final View view) {
        addSubscription(RxView.clicks(view)
                .flatMapCompletable(new Function<Object, Completable>() {

                    @Override
                    public Completable apply(@NonNull Object o) throws Exception {
                        return getItemClickCompletable();
                    }
                })
                .subscribe());
    }

    private void bindEndlessRecyclerViewScrollObservable() {
        addSubscription(new EndlessRecyclerViewScrollObservable(getRecyclerView())
                .flatMap(setPagination())
                .flatMap(loadMoreData())
                .flatMap(calculateRecyclerViewDiffs())
                .subscribe(showResults()));
    }

    private void loadInitialData() {
        addSubscription(loadData()
                .flatMap(calculateRecyclerViewDiffs())
                .subscribe(showResults()));
    }

    private Observable<Transaction<List<T>>> loadData() {
        return getLoadObservable().map(addAdapterItemTypeToElements());
    }

    private Function<Transaction<List<T>>, Transaction<List<T>>> addAdapterItemTypeToElements() {
        return new Function<Transaction<List<T>>, Transaction<List<T>>>() {

            @Override
            public Transaction<List<T>> apply(@NonNull Transaction<List<T>> transaction) throws Exception {
                Transaction<List<T>> newTransaction = transaction;

                if (transaction.isSuccess()) {
                    List<T> list = new ArrayList<>();

                    for (T element : transaction.getData()) {
                        element.setType(AdapterItem.Type.ITEM);
                        list.add(element);
                    }

                    transaction.setData(list);
                    newTransaction = transaction;
                }

                return newTransaction;
            }
        };
    }

    private Function<Integer, Observable<Object>> setPagination() {
        return new Function<Integer, Observable<Object>>() {

            @Override
            public Observable<Object> apply(@NonNull Integer page) throws Exception {
                return getPaginationObservable(page);
            }
        };
    }

    private Function<Object, Observable<Transaction<List<T>>>> loadMoreData() {
        return new Function<Object, Observable<Transaction<List<T>>>>() {


            @Override
            public Observable<Transaction<List<T>>> apply(@NonNull Object o) throws Exception {
                return loadData();
            }
        };
    }

    private Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>> calculateRecyclerViewDiffs() {
        return new Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>>() {

            @Override
            public Observable<DiffUtil.DiffResult> apply(final @NonNull Transaction<List<T>> transaction) throws Exception {
                Observable<DiffUtil.DiffResult> diffResultObservable = Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<DiffUtil.DiffResult> observer) throws Exception {
                        observer.onNext(calculateRecyclerViewDiffDiffResult(transaction.isSuccess() ? transaction.getData() : null));
                        observer.onComplete();
                    }
                }).doOnComplete(new Action() {

                    @Override
                    public void run() throws Exception {
                        if (!transaction.isSuccess()) {
                            getSnackBar().show();
                        }
                    }
                });

                return diffResultObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private Function<Object, Observable<DiffUtil.DiffResult>> calculateRecyclerViewDiffsWithLoading() {
        return new Function<Object, Observable<DiffUtil.DiffResult>>() {

            @Override
            public Observable<DiffUtil.DiffResult> apply(@NonNull Object o) throws Exception {
                return Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<DiffUtil.DiffResult> observer) throws Exception {
                        observer.onNext(calculateRecyclerViewDiffDiffResultWithLoading());
                        observer.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData) {
        List<T> oldList = new LinkedList<>(mList);

        if (showLoadingView()) {
            if (mList.size() > 0 && mList.get(mList.size() - 1).getType().equals(AdapterItem.Type.LOADING)) {
                mList.remove(mList.size() - 1);
            }
        }

        if (newData != null) {
            if (newData.size() > 0) {

                mList.addAll(newData);

                if (showLoadingView()) {
                    mList.add(getLoadingObject());
                }
            } else {
                if (showFooterView()) {
                    mList.add(getFooterObject());
                }
            }
        }

        return DiffUtil.calculateDiff(new RecyclerViewDiffUtilCallback<>(oldList, mList));
    }

    private DiffUtil.DiffResult calculateRecyclerViewDiffDiffResultWithLoading() {
        List<T> oldList = new LinkedList<>(mList);

        if (showLoadingView()) {
            mList.add(getLoadingObject());
        }

        return DiffUtil.calculateDiff(new RecyclerViewDiffUtilCallback<>(oldList, mList));
    }

    private Consumer<DiffUtil.DiffResult> showResults() {
        return new Consumer<DiffUtil.DiffResult>() {

            @Override
            public void accept(@NonNull DiffUtil.DiffResult diffResult) throws Exception {
                getDiffResultBinder().setData(diffResult);
            }
        };
    }

    private RecyclerView getRecyclerView() {
        return getView().getRecyclerView();
    }

    private Snackbar getSnackBar() {
        return getView().getSnackbar();
    }

    private Boolean showLoadingView() {
        return getView().showLoadingView();
    }

    private Boolean showFooterView() {
        return getView().showFooterView();
    }

    private PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return getView().getDiffResultBinder();
    }
}