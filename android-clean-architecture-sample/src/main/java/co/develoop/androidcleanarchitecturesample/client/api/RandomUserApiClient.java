package co.develoop.androidcleanarchitecturesample.client.api;

import java.util.List;

import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApiClient {

    @GET("api")
    Observable<List<RandomUser>> getRandomUserList(@Query("results") Integer results, @Query("page") Integer page, @Query("seed") String seed);
}