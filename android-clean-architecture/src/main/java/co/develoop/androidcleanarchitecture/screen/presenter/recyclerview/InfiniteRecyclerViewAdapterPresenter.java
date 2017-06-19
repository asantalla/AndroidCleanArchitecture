package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.view.recycler.EndlessRecyclerViewScrollObservable;
import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewDiffUtilCallback;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public abstract class InfiniteRecyclerViewAdapterPresenter<V extends InfiniteRecyclerViewAdapterPresenterView, T extends RecyclerViewAdapterItem> extends RecyclerViewAdapterPresenter<V, T> {

    private List<T> mFooterList;

    @Override
    protected void initialize() {
        bindEndlessRecyclerViewScrollObservable();

        mFooterList = getFooterList();
        if (mFooterList == null) {
            mFooterList = new ArrayList<>();
        }
    }

    private void bindEndlessRecyclerViewScrollObservable() {
        addSubscription(new EndlessRecyclerViewScrollObservable(getView().getRecyclerView(), hasLoadingView())
                .flatMap(setPagination())
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

    private Function<Integer, Observable<Object>> setPagination() {
        return new Function<Integer, Observable<Object>>() {

            @Override
            public Observable<Object> apply(@NonNull Integer page) throws Exception {
                return getPaginationObservable(page);
            }
        };
    }

    private Boolean hasFooterView() {
        return mFooterList != null && !mFooterList.isEmpty();
    }

    private RecyclerViewAdapterItem.Type getLastItemType() {
        return mList.get(mList.size() - 1).getType();
    }

    @Override
    public DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData) {
        List<T> oldList = new LinkedList<>(mList);

        if (!mList.isEmpty()) {
            if (getLastItemType().equals(RecyclerViewAdapterItem.Type.LOADING) || getLastItemType().equals(RecyclerViewAdapterItem.Type.ERROR) || getLastItemType().equals(RecyclerViewAdapterItem.Type.FOOTER)) {
                mList.remove(mList.size() - 1);
            }
        }

        if (newData != null && !newData.isEmpty()) {
            mList.addAll(newData);
        } else {
            if (hasFooterView()) {
                mList.addAll(mFooterList);
            }
        }

        return DiffUtil.calculateDiff(new RecyclerViewDiffUtilCallback<>(oldList, mList));
    }

    public abstract List<T> getFooterList();

    public abstract Observable<Object> getPaginationObservable(int page);
}