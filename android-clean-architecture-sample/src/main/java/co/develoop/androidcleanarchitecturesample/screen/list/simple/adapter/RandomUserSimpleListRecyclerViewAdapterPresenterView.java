package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.RecyclerViewAdapterPresenterView;

interface RandomUserSimpleListRecyclerViewAdapterPresenterView extends RecyclerViewAdapterPresenterView {

    void showName(String name);
}