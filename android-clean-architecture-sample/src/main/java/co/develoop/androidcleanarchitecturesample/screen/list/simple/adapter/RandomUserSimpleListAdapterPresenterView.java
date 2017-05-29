package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.SimpleAdapterPresenterView;

interface RandomUserSimpleListAdapterPresenterView extends SimpleAdapterPresenterView {

    PresenterAction showName(String name);
}