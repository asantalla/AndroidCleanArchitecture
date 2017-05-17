package co.develoop.androidcleanarchitecture.screen.presenter;

import android.support.v7.util.DiffUtil;

import co.develoop.androidcleanarchitecture.screen.presenter.action.PresenterViewBinder;

public interface AdapterPresenterView extends PresenterView {

    PresenterViewBinder<DiffUtil.DiffResult> getDiffResultBinder();
}