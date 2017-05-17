package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.injection;

import co.develoop.androidcleanarchitecturesample.injection.AppComponent;
import co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.RandomUserSimpleListAdapter;
import dagger.Component;

@RandomUserSimpleListAdapterScope
@Component(dependencies = {AppComponent.class}, modules = {RandomUserSimpleListAdapterModule.class})
public interface RandomUserSimpleListAdapterComponent {

    void inject(RandomUserSimpleListAdapter randomUserListAdapter);
}