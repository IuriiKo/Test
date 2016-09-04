package com.example.xaocu.test.activity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.Toast;

import com.example.xaocu.test.Logger;
import com.example.xaocu.test.R;
import com.example.xaocu.test.mvp.BaseMVPView;
import com.example.xaocu.test.mvp.BaseMvpActivity;
import com.example.xaocu.test.mvp.view.MainView;
import com.example.xaocu.test.presenter.MainPresenter;

public class MainActivity extends BaseMvpActivity<MainView,MainPresenter> implements MainView {
private static final String LOG_TAG = Logger.createTag(MainActivity.class);
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPresenter().doSome();
  }

  @NonNull
  @Override
  public MainPresenter createPresenter() {
    return new MainPresenter(this);
  }


  @Override
  public void getSomeSuccess() {
    Toast.makeText(this, "Connect to server is success", Toast.LENGTH_LONG).show();
  }

  @Override
  public void getSomeError(Throwable t) {
    Toast.makeText(this, "Can't connect to the server", Toast.LENGTH_LONG).show();
  }
}
