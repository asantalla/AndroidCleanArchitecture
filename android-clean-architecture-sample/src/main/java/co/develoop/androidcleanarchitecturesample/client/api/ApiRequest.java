package co.develoop.androidcleanarchitecturesample.client.api;

import java.util.concurrent.TimeUnit;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.client.transaction.TransactionStatus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ApiRequest<T> {

    private Observable<T> mObservable;

    public ApiRequest(Observable<T> observable) {
        mObservable = observable;
    }

    public Observable<Transaction<T>> get() {
        return mObservable
                .flatMap(new Function<T, Observable<Transaction<T>>>() {

                    @Override
                    public Observable<Transaction<T>> apply(@NonNull T data) throws Exception {
                        return createObservableWithTransaction(data, TransactionStatus.SUCCESS);
                    }
                })
                .onExceptionResumeNext(new Observable<Transaction<T>>() {

                    @Override
                    protected void subscribeActual(Observer<? super Transaction<T>> observer) {
                        observer.onNext(new Transaction<T>(null, TransactionStatus.ERROR));
                        observer.onComplete();
                    }
                })
                .timeout(20, TimeUnit.SECONDS, new Observable<Transaction<T>>() {

                    @Override
                    protected void subscribeActual(Observer<? super Transaction<T>> observer) {
                        observer.onNext(new Transaction<T>(null, TransactionStatus.TIMEOUT));
                        observer.onComplete();
                    }
                });
    }

    private Observable<Transaction<T>> createObservableWithTransaction(final T data, final TransactionStatus status) {
        return Observable.create(new ObservableOnSubscribe<Transaction<T>>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Transaction<T>> observableEmitter) throws Exception {
                Transaction<T> transaction = new Transaction<>(data, status);
                observableEmitter.onNext(transaction);
                observableEmitter.onComplete();
            }
        });
    }
}