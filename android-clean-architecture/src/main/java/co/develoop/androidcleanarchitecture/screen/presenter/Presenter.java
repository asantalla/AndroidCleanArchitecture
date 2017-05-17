package co.develoop.androidcleanarchitecture.screen.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class Presenter<V extends PresenterView> {

    private CompositeDisposable mCompositeDisposable;

    private V mPresenterView;

    public void init(V presenterView) {
        mPresenterView = presenterView;
        mCompositeDisposable = new CompositeDisposable();
        init();
    }

    public V getView() {
        return mPresenterView;
    }

    public void clear() {
        mPresenterView = null;
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    protected void addSubscription(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    protected abstract void init();
}