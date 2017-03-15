package com.shawn.rxretrodemo.api;

import com.shawn.rxretrodemo.entity.MovieEntity;
import com.shawn.rxretrodemo.entity.SearchEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * author: Shawn
 * time  : 2016/11/22 14:59
 */

public interface RxMovieService {
    @GET("movie/top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("book/{search}")
    Observable<SearchEntity> getSearchBooks(@Path("search") String path, @QueryMap Map<String, String> options);
    //    Observable<SearchEntity> getSearchBooks(@Query("q") String q, @Query("start") String start, @Query("count")
    // String count);

    @FormUrlEncoded
    @POST("book/reviews")
    Observable<String> addReviews(@Field("book") String bookId, @Field("title") String title, @Field("content") String content, @Field("rating") String rating);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

}
