package co.develoop.androidcleanarchitecture.screen.view.recycler;

import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class EndlessRecyclerViewScrollObservable extends Observable<Integer> {

    private RecyclerView mRecyclerView;
    private boolean mHasLoadingView;

    public EndlessRecyclerViewScrollObservable(RecyclerView recyclerView, boolean hasLoadingView) {
        mRecyclerView = recyclerView;
        mHasLoadingView = hasLoadingView;
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mRecyclerView.getLayoutManager(), observer, mHasLoadingView);
        mRecyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
    }
}