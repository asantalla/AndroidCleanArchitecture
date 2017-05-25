package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import co.develoop.androidcleanarchitecture.screen.presenter.InfiniteAdapterPresenterView;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;

public interface RandomUserInfiniteListAdapterPresenterView extends InfiniteAdapterPresenterView {

    PresenterAction showMessage();
}