package co.develoop.androidcleanarchitecturesample.domain.repository.user;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import co.develoop.androidcleanarchitecture.client.transaction.Transaction;
import co.develoop.androidcleanarchitecturesample.client.api.ApiRequest;
import co.develoop.androidcleanarchitecturesample.client.api.RandomUserApiClient;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import io.reactivex.Observable;

public class RandomUserApiRepository {

    private RandomUserApiClient mRandomUserApiClient;

    private SecureRandom mRandom;

    public RandomUserApiRepository(RandomUserApiClient randomUserApiClient) {
        mRandomUserApiClient = randomUserApiClient;
        mRandom = new SecureRandom();
    }

    public Observable<Transaction<List<RandomUser>>> getRandomUserList(Integer page) {
        return new ApiRequest<>(mRandomUserApiClient.getRandomUserList(10, page, seed())).get();
    }

    private String seed() {
        return new BigInteger(130, mRandom).toString(32);
    }
}