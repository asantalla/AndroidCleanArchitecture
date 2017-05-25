package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.AdapterPresenterView;

public interface RandomUserSimpleListAdapterPresenterView extends AdapterPresenterView {

    PresenterAction showName(String name);
}