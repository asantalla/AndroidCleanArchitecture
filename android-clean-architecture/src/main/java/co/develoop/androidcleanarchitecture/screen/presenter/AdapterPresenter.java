package co.develoop.androidcleanarchitecture.screen.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AdapterPresenter<V extends PresenterView> {

    private CompositeDisposable mCompositeDisposable;

    private V mPresenterView;

    public void init(V presenterView) {
        initPresenter(presenterView);
        init();
    }

    public void initWithoutLoad(V presenterView) {
        initPresenter(presenterView);
        initWithoutLoad();
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

    protected abstract void initWithoutLoad();

    private void initPresenter(V presenterView) {
        mPresenterView = presenterView;
        mCompositeDisposable = new CompositeDisposable();
    }
}