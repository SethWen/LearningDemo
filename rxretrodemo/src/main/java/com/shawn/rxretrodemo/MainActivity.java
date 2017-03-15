package com.shawn.rxretrodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.shawn.rxretrodemo.entity.MovieEntity;
import com.shawn.rxretrodemo.entity.SearchEntity;
import com.shawn.rxretrodemo.api.MovieService;
import com.shawn.rxretrodemo.api.RxMovieService;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.click_me_BN)
    Button clickMeBN;
    @BindView(R.id.result_TV)
    TextView resultTV;
    private RxMovieService movieService;
    private Object searchBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRetrofit();
    }

    private void initRetrofit() {
        String baseUrl = "https://api.douban.com/v2/";

        OkHttpClient httpClient;
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.e(TAG,
                "log::::::::::::::::: " + message));

        loggingInterceptor.setLevel(level);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        movieService = retrofit.create(RxMovieService.class);
    }

    @OnClick(R.id.click_me_BN)
    public void onClick() {
                getTopMovie();
//        getSearchBooks();
//        addReviews();
    }

    private void addReviews() {
        movieService.addReviews("1","Red","red","5")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                    }
                });
    }

    /**
     * get 请求测试 涉及到 @GET 和 @Query 注解
     */
    private void getTopMovie() {
        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: " + System.currentTimeMillis());
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultTV.setText(e.getMessage());
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        resultTV.setText(movieEntity.toString());
                    }
                });
    }

    //进行网络请求
    private void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        //        String baseUrl = "http://api.yiyahanyu.com/";

        OkHttpClient httpClient;
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.e(TAG,
                "log::::::::::::::::: " + message));

        loggingInterceptor.setLevel(level);
        httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getTopMovie(0, 10);

        Log.d(TAG, "startTime:" + System.currentTimeMillis());
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                MovieEntity body = response.body();
                String title = body.getSubjects().get(0).getTitle();
                Log.d(TAG, "onResponse: " + title);
                Log.d(TAG, "endTime:" + System.currentTimeMillis());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                resultTV.setText(t.getMessage());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    /**
     * get 请求测试 涉及到 @GET 和 @QueryMap 注解
     */
    public void getSearchBooks() {
        Map<String, String> map = new HashMap<>();
        map.put("q", "red");
        //        map.put("tag", null);
        map.put("start", "0");
        map.put("count", "3");
        movieService.getSearchBooks("search", map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<SearchEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(SearchEntity searchEntity) {
                        Log.d(TAG, "onNext: " + searchEntity.toString());
                    }
                });
    }
}
