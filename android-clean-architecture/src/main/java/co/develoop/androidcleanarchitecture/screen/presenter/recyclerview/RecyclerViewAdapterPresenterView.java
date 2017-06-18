package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.support.v7.util.DiffUtil;

import co.develoop.androidcleanarchitecture.screen.presenter.PresenterView;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;

public interface RecyclerViewAdapterPresenterView extends PresenterView {

    PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder();
}