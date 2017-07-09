package com.example.bill.delta.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.R;
import com.example.bill.delta.ui.base.BaseActivity;

/**
 * search screen.
 */

public class SearchActivity  extends BaseActivity {

  private static final String TAG = "SearchActivity";

  @BindView(R.id.searchView) SearchView searchView;
  @BindView(R.id.appbar) AppBarLayout appbar;
  @BindView(R.id.toolbar) Toolbar toolbar;

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, SearchActivity.class);
    callingIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    setupSearchView();
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  public void setupSearchView() {

    searchView.setIconifiedByDefault(true);
    searchView.onActionViewExpanded();
    searchView.requestFocus();
    searchView.setSubmitButtonEnabled(true);
    searchView.setFocusable(true);
    searchView.setIconified(false);
    searchView.requestFocusFromTouch();

    // searchView.setFocusable(false);
    // searchView.clearFocus();
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
  }
}
