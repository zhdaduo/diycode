package com.example.bill.delta.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bill.delta.R;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.util.LogUtil;
import com.github.chrisbanes.photoview.PhotoView;
/**
 *  image screen.
 */

public  class ImageActivity extends BaseActivity {
  public static final String URL = "url";
  private static final String TAG = "ImageActivity";
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.image) PhotoView image;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_image);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    String url = getIntent().getStringExtra(URL);
    LogUtil.d(TAG, "url: " + url);

    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .listener(new RequestListener<String, GlideDrawable>() {
          @Override
          public boolean onException(Exception e, String model, Target<GlideDrawable> target,
              boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
          }

          @Override public boolean onResourceReady(GlideDrawable resource, String model,
              Target<GlideDrawable> target, boolean isFromMemoryCache,
              boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
          }
        })
        .into(image);
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }
}
