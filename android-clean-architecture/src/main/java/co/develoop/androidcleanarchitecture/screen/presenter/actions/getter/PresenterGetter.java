package co.develoop.androidcleanarchitecture.screen.presenter.actions.getter;

import java.util.List;

public interface PresenterGetter<R> {

    R getData();

    void showErrors(List<PresenterGetterError> errors);
}