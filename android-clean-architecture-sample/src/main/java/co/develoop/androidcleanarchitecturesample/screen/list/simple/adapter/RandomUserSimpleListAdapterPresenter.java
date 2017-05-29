package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import java.util.ArrayList;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.AdapterItem;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.SimpleAdapterPresenter;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RandomUserSimpleListAdapterPresenter extends SimpleAdapterPresenter<RandomUserSimpleListAdapterPresenterView, RandomUser> {

    private LoadRandomUserListUseCase mLoadRandomUserListUseCase;

    public RandomUserSimpleListAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase) {
        mLoadRandomUserListUseCase = loadRandomUserListUseCase;
    }

    @Override
    public Observable<Transaction<List<RandomUser>>> getLoadObservable() {
        return mLoadRandomUserListUseCase.bind();
    }

    @Override
    public Completable getItemClickCompletable(final RandomUser randomUser) {
        return Completable.create(new CompletableOnSubscribe() {

            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                getView().showName(randomUser.getName().toString()).execute();
                e.onComplete();
            }
        });
    }

    @Override
    public List<RandomUser> getLoadingList() {
//        List<RandomUser> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            RandomUser fakeItem = new RandomUser("fake" + i + "@gmail.com");
//            fakeItem.setType(AdapterItem.Type.FAKE);
//            fakeList.add(fakeItem);
//        }
//
//        return fakeList;
        List<RandomUser> loadingList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("loading@gmail.com");
        fakeItem.setType(AdapterItem.Type.LOADING);
        loadingList.add(fakeItem);

        return loadingList;
    }

    @Override
    public List<RandomUser> getNetworkErrorList() {
        List<RandomUser> networkErrorList = new ArrayList<>();

        RandomUser fakeItem = new RandomUser("error@gmail.com");
        fakeItem.setType(AdapterItem.Type.ERROR);
        networkErrorList.add(fakeItem);

        return networkErrorList;
    }
}