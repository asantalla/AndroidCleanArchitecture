package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.RecyclerViewAdapterPresenterView;

interface RandomUserSimpleListRecyclerViewAdapterPresenterView extends RecyclerViewAdapterPresenterView {

    PresenterAction showName(String name);
}