package com.example.bill.delta.ui.base;

/**
 * base interface of presenter layer.
 */

public interface BasePresenter<T> {

  void bind(T view);

  void unbind();

  void start();

  void stop();
}
