package com.example.xaocu.test.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.xaocu.test.TestApp;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class NetUtils {
  public static boolean isConnected() {
    ConnectivityManager cm = (ConnectivityManager) TestApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
  }
}
