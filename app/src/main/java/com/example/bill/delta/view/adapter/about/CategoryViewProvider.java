package com.example.bill.delta.view.adapter.about;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bill.delta.R;
import me.drakeet.multitype.ItemViewProvider;

public class CategoryViewProvider
    extends ItemViewProvider<Category, CategoryViewProvider.ViewHolder> {

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.about_page_item_category, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category category) {

    holder.category.setText(category.value);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    TextView category;

    ViewHolder(View itemView) {
      super(itemView);
      category = (TextView) itemView.findViewById(R.id.category);
    }
  }
}
