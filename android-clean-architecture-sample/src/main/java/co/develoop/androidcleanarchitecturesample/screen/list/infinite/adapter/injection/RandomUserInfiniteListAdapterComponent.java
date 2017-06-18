package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.injection;

import co.develoop.androidcleanarchitecturesample.injection.AppComponent;
import co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.RandomUserInfiniteListRecyclerViewAdapter;
import dagger.Component;

@RandomUserInfiniteListAdapterScope
@Component(dependencies = {AppComponent.class}, modules = {RandomUserInfiniteListAdapterModule.class})
public interface RandomUserInfiniteListAdapterComponent {

    void inject(RandomUserInfiniteListRecyclerViewAdapter randomUserInfiniteListAdapter);
}