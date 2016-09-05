package com.example.xaocu.test.net;

import com.example.xaocu.test.model.Feed;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface TestRequestService {
  @GET("api/v3/datasets/WIKI/{name}.json")
  Observable<Feed> getSome(@Path("name") String name) ;
}
