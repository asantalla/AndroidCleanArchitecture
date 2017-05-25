package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface PresenterList<T> {

    Observable<Transaction<List<T>>> getLoadObservable();

    Completable getItemClickCompletable(T data);
}