package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.presenter.AdapterPresenter;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RandomUserSimpleListAdapterPresenter extends AdapterPresenter<RandomUserSimpleListAdapterPresenterView, RandomUser> {

    private LoadRandomUserListUseCase mLoadRandomUserListUseCase;

    public RandomUserSimpleListAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase) {
        mLoadRandomUserListUseCase = loadRandomUserListUseCase;
    }

    @Override
    public Observable<Transaction<List<RandomUser>>> getLoadObservable() {
        return mLoadRandomUserListUseCase.bind();
    }

    @Override
    public Completable getItemClickCompletable() {
        return Completable.create(new CompletableOnSubscribe() {

            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                // Do some stuff
                e.onComplete();
            }
        });
    }
}