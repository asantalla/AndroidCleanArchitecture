package co.develoop.androidcleanarchitecturesample.injection.module.domain;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUserModel;
import co.develoop.androidcleanarchitecturesample.domain.repository.user.RandomUserApiRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    public RandomUserModel randomUserModel(RandomUserApiRepository randomUserApiRepository) {
        return new RandomUserModel(randomUserApiRepository);
    }
}