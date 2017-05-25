package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.presenter.AdapterItem;
import co.develoop.androidcleanarchitecture.screen.presenter.InfiniteAdapterPresenter;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import co.develoop.androidcleanarchitecturesample.usecase.user.SetLoadRandomUserListPaginationUseCase;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RandomUserInfiniteListAdapterPresenter extends InfiniteAdapterPresenter<RandomUserInfiniteListAdapterPresenterView, RandomUser> {

    private LoadRandomUserListUseCase mLoadRandomUserListUseCase;
    private SetLoadRandomUserListPaginationUseCase mSetLoadRandomUserListPaginationUseCase;

    public RandomUserInfiniteListAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase, SetLoadRandomUserListPaginationUseCase setLoadRandomUserListPaginationUseCase) {
        mLoadRandomUserListUseCase = loadRandomUserListUseCase;
        mSetLoadRandomUserListPaginationUseCase = setLoadRandomUserListPaginationUseCase;
    }

    @Override
    public Observable<Transaction<List<RandomUser>>> getLoadObservable() {
        return mLoadRandomUserListUseCase.bind();
    }

    @Override
    public Observable<Object> getPaginationObservable(int page) {
        return mSetLoadRandomUserListPaginationUseCase.bind(new SetLoadRandomUserListPaginationUseCase.Params(page));
    }

    @Override
    public Completable getItemClickCompletable() {
        return Completable.create(new CompletableOnSubscribe() {

            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                getView().showMessage().execute();
                e.onComplete();
            }
        });
    }

    @Override
    public RandomUser getLoadingObject() {
        RandomUser randomUser = new RandomUser(null);
        randomUser.setType(AdapterItem.Type.LOADING);
        return randomUser;
    }

    @Override
    public RandomUser getFooterObject() {
        RandomUser randomUser = new RandomUser(null);
        randomUser.setType(AdapterItem.Type.FOOTER);
        return randomUser;
    }
}