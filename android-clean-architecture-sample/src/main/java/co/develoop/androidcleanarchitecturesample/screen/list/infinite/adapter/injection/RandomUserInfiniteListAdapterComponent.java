package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.injection;

import co.develoop.androidcleanarchitecturesample.injection.AppComponent;
import co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.RandomUserInfiniteListAdapter;
import dagger.Component;

@RandomUserInfiniteListAdapterScope
@Component(dependencies = {AppComponent.class}, modules = {RandomUserInfiniteListAdapterModule.class})
public interface RandomUserInfiniteListAdapterComponent {

    void inject(RandomUserInfiniteListAdapter randomUserInfiniteListAdapter);
}