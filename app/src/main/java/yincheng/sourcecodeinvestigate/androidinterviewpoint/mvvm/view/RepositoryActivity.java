package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model.Repository;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.viewmodel.RepositoriesViewModel;
import yincheng.sourcecodeinvestigate.databinding.ActivityRepodetailBinding;
import yincheng.sourcecodeinvestigate.databinding.ItemRepoBinding;

/**
 * Created by yincheng on 2018/5/22/16:59.
 * github:luoyincheng
 */
public class RepositoryActivity extends AppCompatActivity {
   private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

   private ActivityRepodetailBinding repodetailBinding;
   private RepositoriesViewModel repositoriesViewModel;

   public static Intent newIntent(Context context, Repository repository) {
      Intent intent = new Intent(context, RepositoryActivity.class);
      intent.putExtra(EXTRA_REPOSITORY, repository);
      return intent;
   }

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      repodetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_repodetail);
      Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
      repositoriesViewModel = new RepositoriesViewModel(this, repository);
      repodetailBinding.setViewModel(repositoriesViewModel);
   }

   @Override protected void onDestroy() {
      super.onDestroy();
      repositoriesViewModel.destroy();
   }
}
