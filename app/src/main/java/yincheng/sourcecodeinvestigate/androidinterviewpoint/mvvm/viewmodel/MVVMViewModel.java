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
   /**
    * 优点：
    * 1.双向绑定技术，当Model变化时，View-Model会自动更新，View也会自动变化。很好做到数据的一致性，
    * 不用担心，在模块的这一块数据是这个值，在另一块就是另一个值了。所以 MVVM模式有些时候又被称作：
    * model-view-binder模式。
    * 2.View的功能进一步的强化，具有控制的部分功能，若想无限增强它的功能，甚至控制器的全部功几乎都可以
    * 迁移到各个View上（不过这样不可取，那样View干了不属于它职责范围的事情）。View可以像控制器一样
    * 具有自己的View-Model.
    * 3.由于控制器的功能大都移动到View上处理，大大的对控制器进行了瘦身。不用再为看到庞大的控制器逻辑而发愁了。
    * 4.可以对View或ViewController的数据处理部分抽象出来一个函数处理model。这样它们专职页面布局和页面跳转，
    * 它们必然更一步的简化。
    * 5.ViewModel比​​代码隐藏或事件驱动的代码更容易进行单元测试。ViewModel，虽然它听起来更像是 View
    * 而不像 Model，而且这意味着你可以方便地测试，而不用去理会麻烦的 UI 动画和交互。如果你曾经尝试过对 UI
    * 代码进行单元测试，你就知道这会有多么困难
    * 6.(性能非常好)：数据绑定的性能十分好，但是他会趋向于创建大量的全局的本地数据。就这样运行一段时间，
    * 我们就给我们创建的每一个对象都添加了多个绑定。在构建 WPF程序(WPF:Windows Presentation Foundation,
    * 是微软推出的基于Windows 的用户界面框架，属于.NET Framework 3.0的一部分)的时候我们使用了数据绑定，
    * 每个对象都几乎耗费了2k的内存…所以这说明用来实现绑定的逻辑比被绑定的对象本身要重得多。在这个例子中，
    * 我将全部的绑定替换为静态的回调方法，这一个做法差不多节省了 100MB 的内存…！
    * <p>
    * 缺点：1.数据绑定使得 Bug 很难被调试。你看到界面异常了，有可能是你 View 的代码有 Bug，也可能是 Model
    * 的代码有问题。数据绑定使得一个位置的 Bug 被快速传递到别的位置，要定位原始出问题的地方就变得不那么容易了。
    * 2.一个大的模块中，model也会很大，虽然使用方便了也很容易保证了数据的一致性，当时长期持有，不释放内存，
    * 就造成了花费更多的内存。
    * 3.(不利于代码重用)数据双向绑定不利于代码重用。客户端开发最常用的重用是View，但是数据双向绑定技术，
    * 让你在一个View都绑定了
    * 一个model，不同模块的model都不同。那就不能简单重用View了。
    * 4.(难以调试)：对于简单的 UI 来讲，M-V-VM 是一种过度的设计。在复杂的情况下，很难预先设计出足够通用的
    * ViewModel。尽管数据绑定的神奇在于声明式，然而比起命令式的语法，他更难调试，即便你已经在代码中设置了断点
    * (但如果你有很多事件在到处运行，那可能并不会有什么区别)。
    * 5.(耗费更多内存)对于过大的项目，数据绑定需要花费更多的内存。
    */
   private static final String TAG = MVVMViewModel.class.getSimpleName();

   public ObservableInt infoMessageVisibility;
   public ObservableInt progressVisibility;
   public ObservableInt recyclerViewVisibility;
   public ObservableInt searchButtonVisibility;
   public ObservableField<String> infoMessage;

   private Context context;
   private List<Repository> repositories;//数据
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
