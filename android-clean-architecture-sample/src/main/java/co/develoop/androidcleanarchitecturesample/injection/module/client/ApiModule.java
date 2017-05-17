package co.develoop.androidcleanarchitecturesample.injection.module.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import co.develoop.androidcleanarchitecturesample.client.api.RandomUserApiClient;
import co.develoop.androidcleanarchitecturesample.client.api.deserializer.RandomUserListDeserializer;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient okHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public RandomUserApiClient randomUserApiClient(OkHttpClient okHttpClient) {
        Type randomUserListType = new TypeToken<List<RandomUser>>() {
        }.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(randomUserListType, new RandomUserListDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build().create(RandomUserApiClient.class);
    }
}