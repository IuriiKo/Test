package com.example.xaocu.test.net;

import com.example.xaocu.test.Logger;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class NetManager {
  private static final String LOG_TAG = Logger.createTag(NetInterceptor.class);

  private final TestRequestService service;

  public NetManager() {
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new NetInterceptor()).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.quandl.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(client)
        .build();
    service = retrofit.create(TestRequestService.class);
  }

  public TestRequestService getService() {
    return service;
  }

  private class NetInterceptor implements Interceptor {
    private static final String PER_PAGE = "per_page";
    private static final String PER_PAGE_VALUE = "100";
    private static final String SORT_BY = "sort_by";
    private static final String SORT_BY_VALUE = "id";
    public static final String API_KEY = "api_key";

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      HttpUrl url = request.url().newBuilder()
          .addQueryParameter(API_KEY, "mi2CL7WxcRmtvaYdURCx")
          .build();
      Request newRequest = request.newBuilder().url(url).build();
      Logger.d(LOG_TAG,url.toString());
      return chain.proceed(newRequest);
    }
  }
}
