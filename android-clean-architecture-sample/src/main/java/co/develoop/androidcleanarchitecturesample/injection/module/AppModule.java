package co.develoop.androidcleanarchitecturesample.injection.module;

import android.content.Context;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.RandomUsersApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private RandomUsersApplication mApplication;

    public AppModule(RandomUsersApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Context context() {
        return mApplication.getApplicationContext();
    }
}