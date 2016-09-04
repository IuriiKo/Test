package com.example.xaocu.test.net;

import com.example.xaocu.test.model.Comment;

import java.util.Collection;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface TestRequestService {
  @GET("posts/1/comments")
  Observable<Collection<Comment>> getSome() ;
}
