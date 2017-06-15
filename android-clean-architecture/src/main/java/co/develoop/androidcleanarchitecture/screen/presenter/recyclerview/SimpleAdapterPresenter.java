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
import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewDiffUtilCallback;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class SimpleAdapterPresenter<V extends SimpleAdapterPresenterView, T extends AdapterItem> extends Presenter<V> implements PresenterList<T> {

    private List<T> mList;

    @Override
    protected void init() {
        if (mList == null) {
            mList = new LinkedList<>();

            List<T> loadingList = getLoadingList();
            if (loadingList != null && !loadingList.isEmpty()) {
                mList.addAll(loadingList);
            }
        }

        loadData();
    }

    public abstract List<T> getLoadingList();

    public abstract List<T> getNetworkErrorList();

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
                .flatMap(new Function<Object, Observable<DiffUtil.DiffResult>>() {

                    @Override
                    public Observable<DiffUtil.DiffResult> apply(@NonNull Object o) throws Exception {
                        return load();
                    }
                })
                .subscribe(showResults()));
    }

    private void loadData() {
        addSubscription(load().subscribe(showResults()));
    }

    private Observable<DiffUtil.DiffResult> load() {
        return loadLoadingData()
                .flatMap(calculateRecyclerViewDiffs())
                .doOnNext(showResults())
                .flatMap(loadObservableData())
                .flatMap(calculateRecyclerViewDiffs());
    }

    private Observable<Transaction<List<T>>> loadLoadingData() {
        return Observable.create(new ObservableOnSubscribe<Transaction<List<T>>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Transaction<List<T>>> observer) throws Exception {
                observer.onNext(new Transaction<>(getLoadingList(), TransactionStatus.SUCCESS));
                observer.onComplete();
            }
        });
    }

    private Function<DiffUtil.DiffResult, Observable<Transaction<List<T>>>> loadObservableData() {
        return new Function<DiffUtil.DiffResult, Observable<Transaction<List<T>>>>() {

            @Override
            public Observable<Transaction<List<T>>> apply(@NonNull DiffUtil.DiffResult diffResult) throws Exception {
                return getLoadObservable().map(addAdapterItemTypeToElements());
            }
        };
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

    private Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>> calculateRecyclerViewDiffs() {
        return new Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>>() {

            @Override
            public Observable<DiffUtil.DiffResult> apply(final @NonNull Transaction<List<T>> transaction) throws Exception {
                return Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<DiffUtil.DiffResult> observer) throws Exception {
                        observer.onNext(calculateRecyclerViewDiffDiffResult(transaction.isSuccess() ? transaction.getData() : getNetworkErrorList()));
                        observer.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData) {
        List<T> oldList = new ArrayList<>(mList);
        mList = new ArrayList<>(newData);

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

    private PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return getView().getDiffResultBinder();
    }
}