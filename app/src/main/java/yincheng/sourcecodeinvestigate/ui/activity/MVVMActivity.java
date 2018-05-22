package yincheng.sourcecodeinvestigate.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.RepositoryAdapter;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model.Repository;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.viewmodel.MVVMViewModel;
import yincheng.sourcecodeinvestigate.databinding.ActivityMvvmBinding;

/**
 * Created by yincheng on 2018/5/22/15:35.
 * github:luoyincheng
 */
public class MVVMActivity extends AppCompatActivity implements MVVMViewModel.DataListener {

   private ActivityMvvmBinding mvvmBinding;
   private MVVMViewModel mvvmViewModel;

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mvvmBinding = DataBindingUtil.setContentView(this, R
            .layout.activity_mvvm);
      mvvmViewModel = new MVVMViewModel(this, this);
      mvvmBinding.setViewModel(mvvmViewModel);
      setupRecyclerView(mvvmBinding.reposRecyclerView);
   }

   private void setupRecyclerView(RecyclerView recyclerView) {
      RepositoryAdapter adapter = new RepositoryAdapter();
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
   }

   @Override protected void onDestroy() {
      super.onDestroy();
      mvvmViewModel.destroy();
   }

   @Override public void onRepositoriesChanged(List<Repository> repositories) {
      RepositoryAdapter adapter =
            (RepositoryAdapter) mvvmBinding.reposRecyclerView.getAdapter();
      adapter.setRepositories(repositories);
      adapter.notifyDataSetChanged();
      hideSoftKeyboard();
   }

   private void hideSoftKeyboard() {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(mvvmBinding.editTextUsername.getWindowToken(), 0);
   }
}
