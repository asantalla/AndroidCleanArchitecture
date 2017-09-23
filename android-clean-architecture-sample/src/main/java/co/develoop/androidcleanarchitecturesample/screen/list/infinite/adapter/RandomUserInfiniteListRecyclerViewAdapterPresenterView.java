package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenterView;

interface RandomUserInfiniteListRecyclerViewAdapterPresenterView extends InfiniteRecyclerViewAdapterPresenterView {

    void showName(String name);
}