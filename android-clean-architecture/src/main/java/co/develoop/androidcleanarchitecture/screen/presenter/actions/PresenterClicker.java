package co.develoop.androidcleanarchitecture.screen.presenter.actions;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class PresenterClicker<T extends View> {

    private T mClickableView;

    public PresenterClicker(T clickableView) {
        mClickableView = clickableView;
    }

    public Observable<Object> bind() {
        return RxView.clicks(mClickableView);
    }
}