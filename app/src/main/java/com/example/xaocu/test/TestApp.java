package com.example.xaocu.test;

import android.app.Application;

import com.example.xaocu.test.net.NetManager;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class TestApp extends Application {
  private static NetManager manager;
  @Override
  public void onCreate() {
    super.onCreate();
    initNetManager();
  }

  private void initNetManager() {
    manager = new NetManager();
  }

  public static NetManager getManager() {
    return manager;
  }
}
