package com.example.xaocu.test.net;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface TestRequestService {
  @GET
  Observable<Response<ResponseBody>> getSome(@Url String o) ;
}
