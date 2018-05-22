package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;
import yincheng.sourcecodeinvestigate.App;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model.GithubService;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model.Repository;

/**
 * Created by yincheng on 2018/5/22/15:38.
 * github:luoyincheng
 */
public class MVVMViewModel implements ViewModel {
   private static final String TAG = MVVMViewModel.class.getSimpleName();

   public ObservableInt infoMessageVisibility;
   public ObservableInt progressVisibility;
   public ObservableInt recyclerViewVisibility;
   public ObservableInt searchButtonVisibility;
   public ObservableField<String> infoMessage;

   private Context context;
   private List<Repository> repositories;
   private Disposable disposable;
   private DataListener dataListener;
   private String et_username;

   public MVVMViewModel(Context context, DataListener dataListener) {
      this.context = context;
      this.dataListener = dataListener;
      infoMessageVisibility = new ObservableInt(View.VISIBLE);
      progressVisibility = new ObservableInt(View.INVISIBLE);
      recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
      searchButtonVisibility = new ObservableInt(View.GONE);
      infoMessage = new ObservableField<>("Enter a GitHub username above to see its repositories");
   }

   public void setDataListener(DataListener dataListener) {this.dataListener = dataListener;}

   @Override public void destroy() {
      if (disposable != null && !disposable.isDisposed()) disposable.dispose();
      disposable = null;
      context = null;
      dataListener = null;
   }

   public boolean onSearchAction(TextView view, int actionId, KeyEvent event) {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
         String username = view.getText().toString();
         if (username.length() > 0) loadGithubRepos(username);
         return true;
      }
      return false;
   }

   public void onClickSearch(View view) {
      loadGithubRepos(et_username);
   }

   public TextWatcher getUsernameEditTextWatcher() {
      return new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            et_username = charSequence.toString();
            searchButtonVisibility.set(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
         }

         @Override
         public void afterTextChanged(Editable editable) {

         }
      };
   }

   private void loadGithubRepos(String username) {
      progressVisibility.set(View.VISIBLE);
      recyclerViewVisibility.set(View.INVISIBLE);
      infoMessageVisibility.set(View.INVISIBLE);
      if (disposable != null && !disposable.isDisposed()) disposable.dispose();
      GithubService githubService = App.getInstance().getGithubService();
      disposable = githubService.getPublicRepositories(username)
            .subscribeOn(App.getInstance().getDefaultScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<Repository>>() {
               @Override public void accept(List<Repository> repositories) throws Exception {
                  MVVMViewModel.this.repositories = repositories;
               }
            }, new Consumer<Throwable>() {
               @Override public void accept(Throwable throwable) throws Exception {
                  Log.e(TAG, "Error loading GitHub repos ", throwable);
                  progressVisibility.set(View.INVISIBLE);
                  if (isHttp404(throwable)) {
                     infoMessage.set("username not found");
                  } else {
                     infoMessage.set("load repositories failed");
                  }
                  infoMessageVisibility.set(View.VISIBLE);
               }
            }, new Action() {
               @Override public void run() throws Exception {
                  if (dataListener != null) dataListener.onRepositoriesChanged(repositories);
                  progressVisibility.set(View.INVISIBLE);
                  if (!repositories.isEmpty()) {
                     recyclerViewVisibility.set(View.VISIBLE);
                  } else {
                     infoMessage.set("this user has no repos");
                     infoMessageVisibility.set(View.VISIBLE);
                  }
               }
            });
//      githubService.getPublicRepositories(username)
//            .subscribeOn(App.getInstance().getDefaultScheduler())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Observer<List<Repository>>() {
//               @Override public void onSubscribe(Disposable d) {
//
//               }
//
//               @Override public void onNext(List<Repository> repositories) {
//                  MVVMViewModel.this.repositories = repositories;
//               }
//
//               @Override public void onError(Throwable e) {
//                  Log.e(TAG, "Error loading GitHub repos ", e);
//                  progressVisibility.set(View.INVISIBLE);
//                  if (isHttp404(e)) {
//                     infoMessage.set("username not found");
//                  } else {
//                     infoMessage.set("load repositories failed");
//                  }
//                  infoMessageVisibility.set(View.VISIBLE);
//               }
//
//               @Override public void onComplete() {
//                  if (dataListener != null) dataListener.onRepositoriesChanged(repositories);
//                  progressVisibility.set(View.INVISIBLE);
//                  if (!repositories.isEmpty()) {
//                     recyclerViewVisibility.set(View.VISIBLE);
//                  } else {
//                     infoMessage.set("this user has no repos");
//                     infoMessageVisibility.set(View.VISIBLE);
//                  }
//               }
//            });
   }

   private static boolean isHttp404(Throwable error) {
      return error instanceof HttpException && ((HttpException) error).code() == 404;
   }

   public interface DataListener {
      void onRepositoriesChanged(List<Repository> repositories);
   }
}
