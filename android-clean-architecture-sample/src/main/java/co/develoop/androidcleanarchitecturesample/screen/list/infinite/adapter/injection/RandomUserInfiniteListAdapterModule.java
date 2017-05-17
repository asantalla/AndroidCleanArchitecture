package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.injection;

import co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.RandomUserInfiniteListAdapterPresenter;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import co.develoop.androidcleanarchitecturesample.usecase.user.SetLoadRandomUserListPaginationUseCase;
import dagger.Module;
import dagger.Provides;

@Module
public class RandomUserInfiniteListAdapterModule {

    @Provides
    @RandomUserInfiniteListAdapterScope
    public RandomUserInfiniteListAdapterPresenter randomUserInfiniteListAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase, SetLoadRandomUserListPaginationUseCase setLoadRandomUserListPaginationUseCase) {
        return new RandomUserInfiniteListAdapterPresenter(loadRandomUserListUseCase, setLoadRandomUserListPaginationUseCase);
    }
}