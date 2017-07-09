package com.example.bill.delta.view.Listener;

import android.view.View;

/**
 * Listener Interface for
 * {@link com.example.bill.delta.view.adapter.about.CardViewProvider}.
 * {@link com.example.bill.delta.view.adapter.about.ContributorViewProvider}.
 * {@link com.example.bill.delta.view.adapter.about.LicenseViewProvider}.
 */

public interface IAboutListener {

  void CardListener(View view);

  void ContributorListener(View view, String url);

  void LicenseListener(View view, String url);

}
