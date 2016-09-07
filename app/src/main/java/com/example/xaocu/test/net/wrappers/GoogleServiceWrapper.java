package com.example.xaocu.test.net.wrappers;

import com.example.xaocu.test.net.GoogleService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class GoogleServiceWrapper extends BaseServiceWrapper<GoogleService> {
  public static GoogleService createServise(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://www.google.com/")
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(GoogleService.class);
  }

  public GoogleServiceWrapper(GoogleService quandlService) {
    super(quandlService);
  }

  @Override
  public Observable<ResponseBody> getData(String constraint) {
    return service.getData(constraint);
  }
}
