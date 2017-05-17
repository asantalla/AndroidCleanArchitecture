package co.develoop.androidcleanarchitecturesample.domain.model.user;

import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecturesample.domain.repository.user.RandomUserApiRepository;
import io.reactivex.Observable;

public class RandomUserModel {

    private RandomUserApiRepository mRandomUserApiRepository;

    public RandomUserModel(RandomUserApiRepository randomUserApiRepository) {
        mRandomUserApiRepository = randomUserApiRepository;
    }

    public Observable<Transaction<List<RandomUser>>> getUserList(Integer page) {
        return mRandomUserApiRepository.getRandomUserList(page);
    }
}