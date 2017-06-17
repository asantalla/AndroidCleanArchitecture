package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.SimpleRecyclerViewAdapterPresenterView;

interface RandomUserSimpleListRecyclerViewAdapterPresenterView extends SimpleRecyclerViewAdapterPresenterView {

    PresenterAction showName(String name);
}