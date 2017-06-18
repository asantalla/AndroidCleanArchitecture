package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenterView;

interface RandomUserInfiniteListRecyclerViewAdapterPresenterView extends InfiniteRecyclerViewAdapterPresenterView {

    PresenterAction showName(String name);
}