package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
                .flatMap(loadMoreData())
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

    private Function<Object, Observable<DiffUtil.DiffResult>> loadMoreData() {
        return new Function<Object, Observable<DiffUtil.DiffResult>>() {

            @Override
            public Observable<DiffUtil.DiffResult> apply(@NonNull Object o) throws Exception {
                return load();
            }
        };
    }

    private Boolean hasFooterView() {
        return mFooterList != null && mFooterList.size() > 0;
    }

    @Override
    public DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData) {
        List<T> oldList = new LinkedList<>(mList);

        if (hasLoadingView()) {
            if (mList.size() > 0 && mList.get(mList.size() - 1).getType().equals(RecyclerViewAdapterItem.Type.LOADING)) {
                mList.remove(mList.size() - 1);
            }
        }

        if (newData != null) {
            if (newData.size() > 0) {

                mList.addAll(newData);

                if (hasLoadingView()) {
                    mList.addAll(mLoadingList);
                }
            } else {
                if (hasFooterView()) {
                    mList.addAll(mFooterList);
                }
            }
        }

        return DiffUtil.calculateDiff(new RecyclerViewDiffUtilCallback<>(oldList, mList));
    }

    public abstract List<T> getFooterList();

    public abstract Observable<Object> getPaginationObservable(int page);
}