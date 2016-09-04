package com.example.xaocu.test.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class NetManager {
  private final TestRequestService service;

  public NetManager() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    service = retrofit.create(TestRequestService.class);
  }

  public TestRequestService getService() {
    return service;
  }
}
