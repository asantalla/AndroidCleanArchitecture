package co.develoop.androidcleanarchitecturesample.injection.module.usecase;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.domain.service.RandomUserService;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import co.develoop.androidcleanarchitecturesample.usecase.user.SetLoadRandomUserListPaginationUseCase;
import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    public LoadRandomUserListUseCase loadRandomUserListUseCase(RandomUserService randomUserService) {
        return new LoadRandomUserListUseCase(randomUserService);
    }

    @Provides
    @Singleton
    public SetLoadRandomUserListPaginationUseCase setLoadRandomUserListPaginationUseCase(RandomUserService randomUserService) {
        return new SetLoadRandomUserListPaginationUseCase(randomUserService);
    }
}