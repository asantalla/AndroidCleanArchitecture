package co.develoop.androidcleanarchitecturesample.injection.module.domain;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.client.api.RandomUserApiClient;
import co.develoop.androidcleanarchitecturesample.domain.repository.user.RandomUserApiRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ApiRepositoryModule {

    @Provides
    @Singleton
    public RandomUserApiRepository randomUserApiRepository(RandomUserApiClient randomUserApiClient) {
        return new RandomUserApiRepository(randomUserApiClient);
    }
}