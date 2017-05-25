package co.develoop.androidcleanarchitecture.screen.presenter;

import android.support.v7.util.DiffUtil;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;

public interface AdapterPresenterView extends PresenterView {

    PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder();
}