package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.support.v7.util.DiffUtil;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.client.transaction.TransactionStatus;
import co.develoop.androidcleanarchitecture.screen.presenter.Presenter;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class RecyclerViewAdapterPresenter<V extends RecyclerViewAdapterPresenterView, T extends RecyclerViewAdapterItem> extends Presenter<V> {

    List<T> mList;
    private List<T> mLoadingList;
    private List<T> mNetworkErrorList;

    @Override
    protected final void init() {
        if (mList == null) {
            mList = new LinkedList<>();
        }

        mLoadingList = getLoadingList();
        if (mLoadingList == null) {
            mLoadingList = new ArrayList<>();
        }
        mList.addAll(mLoadingList);

        mNetworkErrorList = getNetworkErrorList();
        if (mNetworkErrorList == null) {
            mNetworkErrorList = new ArrayList<>();
        }

        loadData();

        initialize();
    }

    public List<T> getListData() {
        return mList;
    }

    public void bindItemClick(final View view, final T data) {
        addSubscription(RxView.clicks(view)
                .flatMapCompletable(new Function<Object, Completable>() {

                    @Override
                    public Completable apply(@NonNull Object o) throws Exception {
                        return getItemClickCompletable(view, data);
                    }
                })
                .subscribe());
    }

    public void bindReloadDataObservable(Observable<Object> reloadObservable) {
        addSubscription(reloadObservable
                .flatMap(new Function<Object, Observable<Transaction<List<T>>>>() {

                    @Override
                    public Observable<Transaction<List<T>>> apply(@NonNull Object o) throws Exception {
                        return loadLoadingData();
                    }
                })
                .flatMap(calculateRecyclerViewDiffs())
                .doOnNext(showResults())
                .flatMap(new Function<DiffUtil.DiffResult, Observable<DiffUtil.DiffResult>>() {

                    @Override
                    public Observable<DiffUtil.DiffResult> apply(@NonNull DiffUtil.DiffResult diffResult) throws Exception {
                        return load();
                    }
                })
                .subscribe(showResults()));
    }

    Observable<DiffUtil.DiffResult> load() {
        return loadObservableData()
                .flatMap(calculateRecyclerViewDiffs());
    }

    Consumer<DiffUtil.DiffResult> showResults() {
        return new Consumer<DiffUtil.DiffResult>() {

            @Override
            public void accept(@NonNull DiffUtil.DiffResult diffResult) throws Exception {
                getDiffResultBinder().setData(diffResult);
            }
        };
    }

    Boolean hasLoadingView() {
        return mLoadingList != null && !mLoadingList.isEmpty();
    }

    Observable<Transaction<List<T>>> loadLoadingData() {
        return Observable.create(new ObservableOnSubscribe<Transaction<List<T>>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Transaction<List<T>>> observer) throws Exception {
                observer.onNext(new Transaction<>(mLoadingList, TransactionStatus.SUCCESS));
                observer.onComplete();
            }
        });
    }

    Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>> calculateRecyclerViewDiffs() {
        return new Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>>() {

            @Override
            public Observable<DiffUtil.DiffResult> apply(final @NonNull Transaction<List<T>> transaction) throws Exception {
                return Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<DiffUtil.DiffResult> observer) throws Exception {
                        observer.onNext(calculateRecyclerViewDiffDiffResult(transaction.isSuccess() ? transaction.getData() : mNetworkErrorList));
                        observer.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private void loadData() {
        addSubscription(load().subscribe(showResults()));
    }

    private Observable<Transaction<List<T>>> loadObservableData() {
        return getLoadObservable().map(addAdapterItemTypeToElements());
    }

    private Function<Transaction<List<T>>, Transaction<List<T>>> addAdapterItemTypeToElements() {
        return new Function<Transaction<List<T>>, Transaction<List<T>>>() {

            @Override
            public Transaction<List<T>> apply(@NonNull Transaction<List<T>> transaction) throws Exception {
                Transaction<List<T>> newTransaction;

                if (transaction.isSuccess() && transaction.getData() != null) {
                    List<T> list = new ArrayList<>();

                    for (T element : transaction.getData()) {
                        element.setType(RecyclerViewAdapterItem.Type.ITEM);
                        list.add(element);
                    }

                    transaction.setData(list);

                    newTransaction = transaction;
                } else {
                    newTransaction = new Transaction<>(TransactionStatus.ERROR);
                }

                return newTransaction;
            }
        };
    }

    private PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return getView().getDiffResultBinder();
    }

    public abstract List<T> getLoadingList();

    public abstract List<T> getNetworkErrorList();

    public abstract Observable<Transaction<List<T>>> getLoadObservable();

    public abstract Completable getItemClickCompletable(View viewClicked, T data);

    abstract void initialize();

    abstract DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData);
}