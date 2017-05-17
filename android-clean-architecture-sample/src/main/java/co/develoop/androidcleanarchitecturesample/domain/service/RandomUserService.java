package co.develoop.androidcleanarchitecturesample.domain.service;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUserModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class RandomUserService {

    private RandomUserModel mRandomUserModel;

    private Integer userListPagination = 0;

    public RandomUserService(RandomUserModel randomUserModel) {
        mRandomUserModel = randomUserModel;
    }

    public Observable<Transaction<List<RandomUser>>> getUserList() {
        if (userListPagination > -1) {
            return mRandomUserModel.getUserList(userListPagination);
        } else {
            return Observable.empty();
        }
    }

    public Observable<Object> setUserListPagination(final Integer page) {
        return Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                userListPagination = page;
                e.onNext(userListPagination);
                e.onComplete();
            }
        });
    }
}