package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;


/**
 * Created by yincheng on 2018/5/22/14:31.
 * github:luoyincheng
 */
public interface GithubService {
   @GET("users/{username}/repos") Observable<List<Repository>> getPublicRepositories(@Path("username") String username);

   @GET Observable<User> getUserFromUrl(@Url String url);

   static GithubService create() {
      Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
      return retrofit.create(GithubService.class);
   }
}
