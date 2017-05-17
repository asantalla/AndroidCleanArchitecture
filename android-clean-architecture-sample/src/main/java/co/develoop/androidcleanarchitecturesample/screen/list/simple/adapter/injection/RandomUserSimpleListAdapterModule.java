package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.injection;

import co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.RandomUserSimpleListAdapterPresenter;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import dagger.Module;
import dagger.Provides;

@Module
public class RandomUserSimpleListAdapterModule {

    @Provides
    @RandomUserSimpleListAdapterScope
    public RandomUserSimpleListAdapterPresenter randomUserListAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase) {
        return new RandomUserSimpleListAdapterPresenter(loadRandomUserListUseCase);
    }
}