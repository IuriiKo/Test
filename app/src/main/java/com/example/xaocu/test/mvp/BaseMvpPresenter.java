package com.example.xaocu.test.mvp;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public abstract class BaseMvpPresenter<T extends MvpView> implements MvpPresenter<T> {
  private T view;

  public BaseMvpPresenter(T view) {
    attachView(view);
  }

  @Override
  public void attachView(T view) {
    this.view = view;
  }

  @Override
  public void detachView(boolean retainInstance) {
    view = null;
  }

  public T getView() {
    return view;
  }
}
