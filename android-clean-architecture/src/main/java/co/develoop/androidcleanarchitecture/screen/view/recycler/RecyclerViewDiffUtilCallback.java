package co.develoop.androidcleanarchitecture.screen.view.recycler;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class RecyclerViewDiffUtilCallback<T> extends DiffUtil.Callback {

    private List<T> mOldList;
    private List<T> mNewList;

    public RecyclerViewDiffUtilCallback(List<T> oldList, List<T> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mNewList.get(newItemPosition).equals(mOldList.get(oldItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mNewList.get(newItemPosition).equals(mOldList.get(oldItemPosition));
    }
}