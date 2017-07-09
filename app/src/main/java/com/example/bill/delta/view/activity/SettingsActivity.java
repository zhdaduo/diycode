package com.example.bill.delta.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.R;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.view.fragment.SettingsFragment;

/**
 * settings screen.
 */

public class SettingsActivity extends BaseActivity {

  private static final String TAG = "SettingsActivity";
  @BindView(R.id.container) FrameLayout container;
  @BindView(R.id.toolbar) Toolbar toolbar;

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, SettingsActivity.class);
    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_settings);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(R.id.container, new SettingsFragment())
        .commit();
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

}
