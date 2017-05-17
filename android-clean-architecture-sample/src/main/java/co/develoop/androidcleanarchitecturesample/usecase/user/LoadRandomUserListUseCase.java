package co.develoop.androidcleanarchitecturesample.usecase.user;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecture.usecase.UseCase;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.domain.service.RandomUserService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoadRandomUserListUseCase implements UseCase<Observable<Transaction<List<RandomUser>>>> {

    private RandomUserService mRandomUserService;

    public LoadRandomUserListUseCase(RandomUserService randomUserService) {
        mRandomUserService = randomUserService;
    }

    @Override
    public Observable<Transaction<List<RandomUser>>> bind() {
        return mRandomUserService.getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}