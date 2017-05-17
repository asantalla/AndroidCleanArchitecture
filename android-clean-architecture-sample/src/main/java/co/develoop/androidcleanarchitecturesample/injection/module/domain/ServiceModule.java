package co.develoop.androidcleanarchitecturesample.injection.module.domain;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUserModel;
import co.develoop.androidcleanarchitecturesample.domain.service.RandomUserService;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public RandomUserService randomUserService(RandomUserModel randomUserModel) {
        return new RandomUserService(randomUserModel);
    }
}