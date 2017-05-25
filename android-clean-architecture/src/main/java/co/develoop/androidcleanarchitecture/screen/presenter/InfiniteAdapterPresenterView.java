package co.develoop.androidcleanarchitecture.screen.presenter;

import android.support.design.widget.Snackbar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;

public interface InfiniteAdapterPresenterView extends PresenterView {

    RecyclerView getRecyclerView();

    Snackbar getSnackbar();

    Boolean showLoadingView();

    Boolean showFooterView();

    PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder();
}