package co.develoop.androidcleanarchitecturesample.injection;

import android.content.Context;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.injection.module.AppModule;
import co.develoop.androidcleanarchitecturesample.injection.module.client.ApiModule;
import co.develoop.androidcleanarchitecturesample.injection.module.domain.ApiRepositoryModule;
import co.develoop.androidcleanarchitecturesample.injection.module.domain.ModelModule;
import co.develoop.androidcleanarchitecturesample.injection.module.domain.ServiceModule;
import co.develoop.androidcleanarchitecturesample.injection.module.usecase.UseCaseModule;
import co.develoop.androidcleanarchitecturesample.usecase.user.LoadRandomUserListUseCase;
import co.develoop.androidcleanarchitecturesample.usecase.user.SetLoadRandomUserListPaginationUseCase;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        UseCaseModule.class,
        ServiceModule.class,
        ModelModule.class,
        ApiRepositoryModule.class,
        ApiModule.class,})
public interface AppComponent {

    Context provideContext();

    // Use Case

    LoadRandomUserListUseCase provideLoadRandomUserListUseCase();

    SetLoadRandomUserListPaginationUseCase provideSetLoadRandomUserListPaginationUseCase();
}