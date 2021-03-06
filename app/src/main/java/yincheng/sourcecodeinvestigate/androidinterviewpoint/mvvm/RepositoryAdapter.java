package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model.Repository;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.viewmodel.ItemRepoViewModel;
import yincheng.sourcecodeinvestigate.databinding.ItemRepoBinding;

/**
 * Created by yincheng on 2018/5/22/17:29.
 * github:luoyincheng
 */

public class RepositoryAdapter extends
      RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

   private List<Repository> repositories;

   public RepositoryAdapter() {
      this.repositories = Collections.emptyList();
   }

   public RepositoryAdapter(List<Repository> repositories) {
      this.repositories = repositories;
   }

   public void setRepositories(List<Repository> repositories) {
      this.repositories = repositories;
   }

   @Override
   public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      ItemRepoBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_repo,
            parent,
            false);
      return new RepositoryViewHolder(binding);
   }

   @Override
   public void onBindViewHolder(RepositoryViewHolder holder, int position) {
      holder.bindRepository(repositories.get(position));
   }

   @Override
   public int getItemCount() {
      return repositories.size();
   }

   public static class RepositoryViewHolder extends RecyclerView.ViewHolder {
      final ItemRepoBinding binding;//生成的binding既包含了界面控件，也包含了数据设置

      public RepositoryViewHolder(ItemRepoBinding binding) {
         super(binding.cardView);
         this.binding = binding;
      }

      void bindRepository(Repository repository) {
         if (binding.getViewModel() == null) {
            binding.setViewModel(new ItemRepoViewModel(itemView.getContext(), repository));
         } else {
            binding.getViewModel().setRepository(repository);
         }
      }
   }
}

