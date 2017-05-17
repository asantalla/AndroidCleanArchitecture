package co.develoop.androidcleanarchitecture.screen.view.recycler;

import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class EndlessRecyclerViewScrollObservable extends Observable<Integer> {

    private RecyclerView mRecyclerView;

    public EndlessRecyclerViewScrollObservable(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mRecyclerView.getLayoutManager(), observer);
        mRecyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
    }
}