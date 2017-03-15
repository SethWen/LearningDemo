package com.shawn.rxretrodemo.retrodata;

import com.shawn.rxretrodemo.api.MovieService;
import com.shawn.rxretrodemo.api.RxMovieService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: Shawn
 * time  : 2016/11/24 14:51
 */

public class YiyaRetrofitFactory {

    private static MovieService mSingleton;

    public static MovieService getInstance() {
        if (mSingleton != null) {
            synchronized (YiyaRetrofitFactory.class) {
                if (mSingleton != null) {
                    mSingleton = new YiyaRetrofit().getMovieService();
                }
            }
        }
        return mSingleton;
    }

    static class YiyaRetrofit {

        private RxMovieService rxMovieService;
        private MovieService movieService;

        private YiyaRetrofit() {

            // 1. 通过 OkHttp 初始化拦截器
            String baseUrl = "https://api.douban.com/v2/movie/";

            // 2. 初始化 Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            rxMovieService = retrofit.create(RxMovieService.class);
            movieService = retrofit.create(MovieService.class);
        }

        public RxMovieService getRxMovieService() {
            return rxMovieService;
        }

        public MovieService getMovieService() {
            return movieService;
        }
    }
}
