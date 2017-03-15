package com.shawn.rxretrodemo.api;

import com.shawn.rxretrodemo.entity.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Shawn
 * time  : 2016/11/22 14:35
 */

public interface MovieService {
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    //    @POST("order/order_list")
    //    Call<MovieEntity> getTopMovie(@Body User user);
}
