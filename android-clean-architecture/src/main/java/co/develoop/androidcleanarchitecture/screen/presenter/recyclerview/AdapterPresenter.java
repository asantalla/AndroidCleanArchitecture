package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.support.v7.util.DiffUtil;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
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

public abstract class AdapterPresenter<V extends AdapterPresenterView, T> extends Presenter<V> implements PresenterList<T> {

    private List<T> mList;

    @Override
    protected void init() {
        mList = new LinkedList<>();

        loadInitialData();
    }

    public List<T> getListData() {
        return mList;
    }

    public void bindItemClick(final View view, final T data) {
        addSubscription(RxView.clicks(view)
                .flatMapCompletable(new Function<Object, Completable>() {

                    @Override
                    public Completable apply(@NonNull Object o) throws Exception {
                        return getItemClickCompletable(data);
                    }
                })
                .subscribe());
    }

    private void loadInitialData() {
        addSubscription(getLoadObservable()
                .flatMap(calculateRecyclerViewDiffs())
                .subscribe(showResults()));
    }

    private Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>> calculateRecyclerViewDiffs() {
        return new Function<Transaction<List<T>>, Observable<DiffUtil.DiffResult>>() {

            @Override
            public Observable<DiffUtil.DiffResult> apply(final @NonNull Transaction<List<T>> transaction) throws Exception {
                return Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<DiffUtil.DiffResult> observer) throws Exception {
                        observer.onNext(calculateRecyclerViewDiffDiffResult(transaction.isSuccess() ? transaction.getData() : new ArrayList<T>()));
                        observer.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData) {
        List<T> oldList = new LinkedList<>(mList);

        if (newData != null && newData.size() > 0) {
            mList.addAll(newData);
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

    private PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return getView().getDiffResultBinder();
    }
}