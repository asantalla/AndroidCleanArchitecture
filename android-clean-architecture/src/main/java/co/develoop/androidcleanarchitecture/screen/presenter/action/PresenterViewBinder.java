package co.develoop.androidcleanarchitecture.screen.presenter.action;

public interface PresenterViewBinder<T> {

    void setData(T data);

    void clearData();
}