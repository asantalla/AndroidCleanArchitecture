package co.develoop.androidcleanarchitecturesample;

import android.app.Application;

import co.develoop.androidcleanarchitecturesample.injection.AppComponent;
import co.develoop.androidcleanarchitecturesample.injection.DaggerAppComponent;
import co.develoop.androidcleanarchitecturesample.injection.module.AppModule;

public class RandomUsersApplication extends Application {

    private static AppComponent mDaggerAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDaggerAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent daggerAppComponent() {
        return mDaggerAppComponent;
    }
}