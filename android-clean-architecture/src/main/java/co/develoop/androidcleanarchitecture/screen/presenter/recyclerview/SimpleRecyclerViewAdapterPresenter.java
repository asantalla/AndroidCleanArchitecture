package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewDiffUtilCallback;

public abstract class SimpleRecyclerViewAdapterPresenter<V extends RecyclerViewAdapterPresenterView, T extends RecyclerViewAdapterItem> extends RecyclerViewAdapterPresenter<V, T> {

    @Override
    void initialize() {
    }

    @Override
    public DiffUtil.DiffResult calculateRecyclerViewDiffDiffResult(List<T> newData) {
        List<T> oldList = new ArrayList<>(mList);
        mList = new ArrayList<>(newData);

        return DiffUtil.calculateDiff(new RecyclerViewDiffUtilCallback<>(oldList, mList));
    }
}