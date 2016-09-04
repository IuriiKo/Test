package com.example.xaocu.test.mvp;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Unbinder;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public abstract class BaseMvpActivity<V extends BaseMVPView, P extends BaseMvpPresenter<V>> extends MvpActivity<V,P>  {
  protected Unbinder unbinder;

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }
}
