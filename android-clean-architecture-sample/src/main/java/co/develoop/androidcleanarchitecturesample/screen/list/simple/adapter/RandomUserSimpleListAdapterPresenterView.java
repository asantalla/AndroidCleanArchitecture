package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.AdapterPresenterView;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;

public interface RandomUserSimpleListAdapterPresenterView extends AdapterPresenterView {

    PresenterAction showMessage();
}