package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.injection;

import co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.RandomUserSimpleListRecyclerViewAdapterPresenter;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import dagger.Module;
import dagger.Provides;

@Module
public class RandomUserSimpleListAdapterModule {

    @Provides
    @RandomUserSimpleListAdapterScope
    public RandomUserSimpleListRecyclerViewAdapterPresenter randomUserListAdapterPresenter(LoadRandomUserListUseCase loadRandomUserListUseCase) {
        return new RandomUserSimpleListRecyclerViewAdapterPresenter(loadRandomUserListUseCase);
    }
}