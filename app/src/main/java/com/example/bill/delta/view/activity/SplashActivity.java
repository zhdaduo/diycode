package com.example.bill.delta.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.main.MainActivity;
import javax.inject.Inject;

/**
 * splash screen.
 */

public class SplashActivity extends Activity {

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    Window window = getWindow();
    window.setFormat(PixelFormat.RGBA_8888);
  }

  Thread splashThread;

  @Inject Navigator navigator;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ((AndroidApplication) getApplication()).getApplicationComponent().inject(this);
    StartAnimations();
  }

  private void StartAnimations() {
    Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
    anim.reset();
    LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
    l.clearAnimation();
    l.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(this, R.anim.translate);
    anim.reset();
    ImageView iv = (ImageView) findViewById(R.id.splash);
    iv.clearAnimation();
    iv.startAnimation(anim);

    splashThread = new Thread() {
      @Override
      public void run() {
        try {
          int waited = 0;
          while (waited < 2000) {
            sleep(100);
            waited += 100;
          }
          navigator.navigateToMainActivity(SplashActivity.this);
          SplashActivity.this.finish();
        }catch (InterruptedException e) {

        }finally {
          SplashActivity.this.finish();
        }
      }
    };
    splashThread.start();
  }
}
