package com.example.bill.delta;

import android.app.Application;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import com.example.bill.delta.internal.di.components.ApplicationComponent;
import com.example.bill.delta.internal.di.components.DaggerApplicationComponent;
import com.example.bill.delta.internal.di.modules.ApplicationModule;
import com.example.bill.delta.util.Constant;
import com.example.bill.delta.util.KeyStoreHelper;
import com.pgyersdk.crash.PgyCrashManager;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Android Main Application
 */

public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  private String BaseUrl = "https://www.diycode.cc/api/v3/";

  @Override
  public void onCreate() {
    super.onCreate();
    PgyCrashManager.register(this);

    try {
      KeyStoreHelper.createKeys(getApplicationContext(), Constant.KEYSTORE_KEY_ALIAS);
    } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }

    // initialize BGASwipeBackManager
    BGASwipeBackManager.getInstance().init(this);
    this.initializeInjector();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this, BaseUrl))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }
}
