package com.shawn.videocachedemo;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, YiyaVideoPlayer.Callback {

    private static final String TAG = "MainActivity";

    private YiyaVideoPlayer player;
    private VideoView videoView;
    private TextureView textureView;
    private Button btnStart;
    private Button btnStop;
    private SeekBar seekBar;
    private String VIDEO_URL = "http://192.168.1.138:8080/video/test1.mp4";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        videoView = (VideoView) findViewById(R.id.video_view);
         Ha.gaoDuixiang("ss");
//        checkNotNull(config)

        textureView = (TextureView) findViewById(R.id.texture_view);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStop = (Button) findViewById(R.id.btn_stop);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);

//        seekBar.setPadding(0, 0, 0, 0);

//        App.getProxy(this).
        String proxyUrl = App.getProxy(this).getProxyUrl(VIDEO_URL);

        player = new YiyaVideoPlayer(this, textureView);
        player.setSource(Uri.parse(proxyUrl));
        player.setCallback(this);



        //        Log.e(TAG, "onCreate: " + proxyUrl);
//        videoView.setVideoPath(proxyUrl);
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                videoView.start();
//            }
//        });
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.start();
//            }
//        });
//
//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.pause();
//            }
//        });
//
//        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e(TAG, "onProgressChanged: " + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "onStartTrackingTouch: ");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "onStopTrackingTouch: ");
    }

    @Override
    public void onStarted(YiyaVideoPlayer player) {

    }

    @Override
    public void onPaused(YiyaVideoPlayer player) {

    }

    @Override
    public void onPreparing(YiyaVideoPlayer player) {

    }

    @Override
    public void onPrepared(YiyaVideoPlayer player) {
        player.start();
    }

    @Override
    public void onBuffering(int percent) {
        Log.e(TAG, "onBuffering: " + percent);
    }

    @Override
    public void onInfo(MediaPlayer mp, int what, int extra) {

    }

    @Override
    public void onError(YiyaVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(YiyaVideoPlayer player) {

    }

    @Override
    public void onProgressUpdate(int currentPosition, int duration) {

    }

    @Override
    public void onSpecifiedPosition(ArrayList<Integer> positions) {

    }
}
