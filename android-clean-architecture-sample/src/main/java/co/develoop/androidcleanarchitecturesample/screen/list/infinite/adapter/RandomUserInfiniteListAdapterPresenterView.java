package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.InfiniteAdapterPresenterView;

public interface RandomUserInfiniteListAdapterPresenterView extends InfiniteAdapterPresenterView {

    PresenterAction showName(String name);
}