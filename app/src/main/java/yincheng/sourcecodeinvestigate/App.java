package yincheng.sourcecodeinvestigate;

import android.app.Application;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model.GithubService;

/**
 * Created by yincheng on 2018/5/17/18:41.
 * github:luoyincheng
 */
public class App extends Application {
   public static App instance;
   private GithubService githubService;
   private Scheduler scheduler;

   public static App getInstance() {
      return instance;
   }

   @Override public void onCreate() {
      super.onCreate();
      instance = this;
   }

   public GithubService getGithubService() {
      if (githubService == null) githubService = GithubService.create();
      return githubService;
   }

   public Scheduler getDefaultScheduler() {
      if (scheduler == null) scheduler = Schedulers.io();
      return scheduler;
   }


}
