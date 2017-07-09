package com.example.bill.delta.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.internal.di.components.ApplicationComponent;

/**
 * Base {@link AppCompatActivity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity
    implements BGASwipeBackHelper.Delegate {

  protected BGASwipeBackHelper mSwipeBackHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    initSwipeBackFinish();
    super.onCreate(savedInstanceState);
    initActionBar(getToolbar());
  }

  protected abstract Toolbar getToolbar();

  private void initActionBar(Toolbar toolbar) {
    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Shows a {@link android.widget.Toast} message.
   *
   * @param message An string representing a message to be shown.
   */
  protected void showToastMessage(String message) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }

  /**
   *
   * initialize slide back. this method should call before
   * super.onCreate(savedInstanceState)
   */
  private void initSwipeBackFinish() {
    mSwipeBackHelper = new BGASwipeBackHelper(this, this);
  }

  /**
   * default return true, it mean that support slide back function.
   * if you don't need it, Override the method and return false.
   */
  @Override
  public boolean isSupportSwipeBack() {
    return true;
  }

  /**
   * doing swipe back.
   *
   * @param slideOffset  0 ～ 1
   */
  @Override
  public void onSwipeBackLayoutSlide(float slideOffset) {
  }

  /**
   * cancel the sliding back action and back to the default state if not reach the sliding threshold.
   */
  @Override
  public void onSwipeBackLayoutCancel() {
  }

  /**
   * Swipe back complete destroy current activity
   */
  @Override
  public void onSwipeBackLayoutExecuted() {
    mSwipeBackHelper.swipeBackward();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link com.example.bill.delta.internal.di.components.ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }


}
