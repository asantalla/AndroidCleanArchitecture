package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

import android.view.View;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface PresenterList<T extends AdapterItem> {

    Observable<Transaction<List<T>>> getLoadObservable();

    Completable getItemClickCompletable(View viewClicked, T data);
}