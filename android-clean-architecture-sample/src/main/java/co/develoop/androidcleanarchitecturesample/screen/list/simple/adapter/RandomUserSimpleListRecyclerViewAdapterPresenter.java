package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.RecyclerViewAdapterItem;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.SimpleRecyclerViewAdapterPresenter;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RandomUserSimpleListRecyclerViewAdapterPresenter extends SimpleRecyclerViewAdapterPresenter<RandomUserSimpleListRecyclerViewAdapterPresenterView, RandomUser> {

    private LoadRandomUserListUseCase mLoadRandomUserListUseCase;

    public RandomUserSimpleListRecyclerViewAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase) {
        mLoadRandomUserListUseCase = loadRandomUserListUseCase;
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
        List<RandomUser> networkErrorList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("error@gmail.com");
        fakeItem.setType(RecyclerViewAdapterItem.Type.ERROR);
        networkErrorList.add(fakeItem);

        return networkErrorList;
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
                getView().showName(randomUser.getName().toString());
                e.onComplete();
            }
        });
    }
}