package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenter;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.RecyclerViewAdapterItem;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import co.develoop.androidcleanarchitecturesample.usecase.user.SetLoadRandomUserListPaginationUseCase;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RandomUserInfiniteListRecyclerViewAdapterPresenter extends InfiniteRecyclerViewAdapterPresenter<RandomUserInfiniteListRecyclerViewAdapterPresenterView, RandomUser> {

    private LoadRandomUserListUseCase mLoadRandomUserListUseCase;
    private SetLoadRandomUserListPaginationUseCase mSetLoadRandomUserListPaginationUseCase;

    public RandomUserInfiniteListRecyclerViewAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase, SetLoadRandomUserListPaginationUseCase setLoadRandomUserListPaginationUseCase) {
        mLoadRandomUserListUseCase = loadRandomUserListUseCase;
        mSetLoadRandomUserListPaginationUseCase = setLoadRandomUserListPaginationUseCase;
    }

    @Override
    public List<RandomUser> getLoadingList() {
        List<RandomUser> loadingList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("loading@gmail.com");
        fakeItem.setType(RecyclerViewAdapterItem.Type.LOADING);
        loadingList.add(fakeItem);

        return loadingList;
    }

    @Override
    public List<RandomUser> getNetworkErrorList() {
        List<RandomUser> loadingList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("error@gmail.com");
        fakeItem.setType(RecyclerViewAdapterItem.Type.ERROR);
        loadingList.add(fakeItem);

        return loadingList;
    }

    @Override
    public Observable<Transaction<List<RandomUser>>> getLoadObservable() {
        return mLoadRandomUserListUseCase.bind();
    }

    @Override
    public Completable getItemClickCompletable(final View viewClicked, final RandomUser randomUser) {
        return Completable.create(new CompletableOnSubscribe() {

            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                getView().showName(randomUser.getName().toString()).execute();
                e.onComplete();
            }
        });
    }

    @Override
    public List<RandomUser> getFullscreenLoadingList() {
        List<RandomUser> loadingList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("error@gmail.com");
        fakeItem.setType(RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING);
        loadingList.add(fakeItem);

        return loadingList;
    }

    @Override
    public List<RandomUser> getFullscreenNetworkErrorList() {
        List<RandomUser> loadingList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("error@gmail.com");
        fakeItem.setType(RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR);
        loadingList.add(fakeItem);

        return loadingList;
    }

    @Override
    public List<RandomUser> getFooterList() {
        List<RandomUser> loadingList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("footer@gmail.com");
        fakeItem.setType(RecyclerViewAdapterItem.Type.FOOTER);
        loadingList.add(fakeItem);

        return loadingList;
    }

    @Override
    public Observable<Object> getPaginationObservable(int page) {
        return mSetLoadRandomUserListPaginationUseCase.bind(new SetLoadRandomUserListPaginationUseCase.Params(page));
    }
}