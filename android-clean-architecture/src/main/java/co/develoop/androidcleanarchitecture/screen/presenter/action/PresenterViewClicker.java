package co.develoop.androidcleanarchitecture.screen.presenter.action;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public class PresenterViewClicker<T extends View> {

    private T mClickableView;

    public PresenterViewClicker(T clickableView) {
        mClickableView = clickableView;
    }

    public Observable<Object> bind() {
        return RxView.clicks(mClickableView);
    }
}