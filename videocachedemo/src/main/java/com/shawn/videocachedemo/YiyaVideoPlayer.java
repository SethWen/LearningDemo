package com.shawn.videocachedemo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntRange;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 视频播放器的封装
 * <p>
 * author: Shawn
 * time  : 2016/10/1 20:57
 */

public class YiyaVideoPlayer implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnVideoSizeChangedListener, TextureView.SurfaceTextureListener, MediaPlayer.OnInfoListener {

    private static final String TAG = "YiyaVideoPlayer";

    private Context mContext;

    public MediaPlayer mPlayer;
    private TextureView mTextureView;
    private Surface mSurface;
    private ArrayList<Integer> mPositions;

    private boolean mIsPrepared;
    private boolean mWasPlaying;
    private boolean mSurfaceAvailable;

    private int mInitialTextureWidth;
    private int mInitialTextureHeight;

    private Uri mSource;

    private static final int UPDATE_PROCESS_TASK = 0;
    private static final int SPECIFIC_POSITION_TASK = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROCESS_TASK:
                    int currentPosition = getCurrentPosition();
                    int duration = getDuration();
                    updateVideoProgress(currentPosition, duration);
                    break;
                case SPECIFIC_POSITION_TASK:
                    if (isPlaying()) {
                        doSthAtSpecificPostion(mPositions);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 跟新进度视频进度
     *
     * @param currentPosition
     * @param duration
     */
    private void updateVideoProgress(int currentPosition, int duration) {
        //        Log.d(TAG, "updateVideoProgress: " + currentPosition);
        if (mCallback != null) {
            mCallback.onProgressUpdate(currentPosition, duration);
        }

        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(UPDATE_PROCESS_TASK, 500);
        }
    }

    /**
     * 在指定时间调用
     *
     * @param positions
     */
    private void doSthAtSpecificPostion(ArrayList<Integer> positions) {
        if (mCallback != null) {
            mCallback.onSpecifiedPosition(positions);
        }

        if (mHandler != null) {
            mHandler.sendEmptyMessage(SPECIFIC_POSITION_TASK);
        }
    }

    public YiyaVideoPlayer(Context context, TextureView textureView) {
        this.mContext = context;
        this.mTextureView = textureView;

        //            mPlayer.setLooping(true);

        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnVideoSizeChangedListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnInfoListener(this);

        mTextureView.setSurfaceTextureListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable: ");
        mSurfaceAvailable = true;
        mInitialTextureWidth = width;
        mInitialTextureHeight = height;
        mSurface = new Surface(surfaceTexture);
        if (mIsPrepared) {
            mPlayer.setSurface(mSurface);
        } else {
            preparePlayer();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureSizeChanged() called with: " +
                "mSurface = [" + surface + "], width = [" + width + "], " + "height = [" + height + "]");
        adjustAspectRatio(width, height, mPlayer.getVideoWidth(), mPlayer.getVideoHeight());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "onSurfaceTextureDestroyed: ");
        mSurfaceAvailable = false;
        mSurface = null;
        stop();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //        Log.d(TAG, "onSurfaceTextureUpdated: ");
    }

    /**
     * 设置视频资源
     *
     * @param source
     */
    public void setSource(Uri source) {
        this.mSource = source;
        preparePlayer();
    }

    private void preparePlayer() {
        if (!mSurfaceAvailable || mSource == null || mPlayer == null || mIsPrepared) return;

        try {
            if (mCallback != null) {
                mCallback.onPreparing(this);
            }
            mPlayer.setSurface(mSurface);
            mPlayer.setScreenOnWhilePlaying(true);
            if (mSource.getScheme() != null &&
                    (mSource.getScheme().equals("http") || mSource.getScheme().equals("https"))) {
                // 调用网络视频
                Log.i(TAG, "Loading web URI: " + mSource.toString());
                mPlayer.setDataSource(mSource.toString());
            } else {
                // 调用本地视频
                Log.i(TAG, "Loading local URI: " + mSource.toString());
                mPlayer.setDataSource(mContext, mSource);
            }
            mPlayer.prepareAsync();
        } catch (IOException e) {
            throwError(e);
        }
    }

    /**
     * 设置指定视频时间
     *
     * @param mPositions
     */
    public void setPositions(ArrayList<Integer> mPositions) {
        this.mPositions = mPositions;
    }

    /**
     * 开始播放
     */
    public void start() {
        if (mPlayer != null && !isPlaying()) {
            mPlayer.start();
            Log.d(TAG, "视频开始了");
        }
        if (mCallback != null) {
            mCallback.onStarted(this);
        }
        if (mHandler != null) {
            // 开启视频进度更新的线程
            mHandler.sendEmptyMessage(UPDATE_PROCESS_TASK);
            // 开始监控视频播放的指定时间点线程
            if (isPlaying()) {
                //                mHandler.sendEmptyMessage(SPECIFIC_POSITION_TASK);
                // 为了使视频在指定的时间点暂停的时候，此处延时 50ms 发送
                mHandler.sendEmptyMessageDelayed(SPECIFIC_POSITION_TASK, 50);
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mPlayer != null && isPlaying()) {
            mPlayer.pause();
            mHandler.removeMessages(SPECIFIC_POSITION_TASK);
            mHandler.removeMessages(UPDATE_PROCESS_TASK);
            //            mHandler.removeCallbacksAndMessages(null);
        }
        if (mCallback != null) {
            mCallback.onPaused(this);
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        if (mHandler != null) {
            // stop 时移出所有队列消息
            mHandler.removeMessages(SPECIFIC_POSITION_TASK);
            mHandler.removeMessages(UPDATE_PROCESS_TASK);
            mHandler = null;
        }
        Log.d(TAG, "Released player and Handler");
    }


    /**
     * 重置播放器
     */
    public void reset() {
        if (mPlayer != null) {
            mIsPrepared = false;
            mPlayer.reset();
        }
    }

    /**
     * 是否在正在播放
     */
    public boolean isPlaying() {
        if (mPlayer == null) {
            Log.d(TAG, "mPlayer == null");
            return false;
        }
        return mPlayer.isPlaying();
    }

    /**
     * 当前的进度
     */
    public int getCurrentPosition() {
        if (mPlayer != null) {
            return mPlayer.getCurrentPosition();
        }
        return -1;
    }

    /**
     * 获取视频长度
     *
     * @return
     */
    public int getDuration() {
        if (mPlayer != null) {
            return mPlayer.getDuration();
        }
        return -1;
    }

    /**
     * 视频跳转到指定位置
     *
     * @param pos
     */
    public void seekTo(@IntRange(from = 0, to = Integer.MAX_VALUE) int pos) {
        if (mPlayer != null) {
            mPlayer.seekTo(pos);
        }
    }

    /**
     * 抛出异常
     *
     * @param e
     */
    private void throwError(Exception e) {
        if (mCallback != null) {
            mCallback.onError(this, e);
        } else {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mCallback != null) {
            mCallback.onPrepared(this);
        }
        mIsPrepared = true;
        Log.d(TAG, mPlayer.getVideoWidth() + "-------" + mPlayer.getVideoHeight());
        // 显示视频第一帧
        //        start();
        //        pause();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "onBufferingUpdate() called with: mp = [" + mp + "], percent = [" + percent + "]");
        if (mCallback != null) {
            mCallback.onBuffering(percent);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.i(TAG, "onError: ");
        if (what == -38) {
            // Error code -38 happens on some Samsung devices
            // Just ignore it
            return false;
        }
        String errorMsg = "Preparation/playback error (" + what + "): ";
        switch (what) {
            default:
                errorMsg += "Unknown error";
                break;
            case MediaPlayer.MEDIA_ERROR_IO:
                errorMsg += "I/O error";
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                errorMsg += "Malformed";
                break;
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                errorMsg += "Not valid for progressive playback";
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                errorMsg += "Server died";
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                errorMsg += "Timed out";
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                errorMsg += "Unsupported";
                break;
        }
        throwError(new Exception(errorMsg));
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo: ");
        if (mCallback != null) {
            mCallback.onInfo(mp, what, extra);
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: ");
        if (mCallback != null) {
            mCallback.onCompletion(this);
        }
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        adjustAspectRatio(mInitialTextureWidth, mInitialTextureHeight, width, height);
    }

    /**
     * 根据 TextureView 和 视频的比例，适配视频长宽
     *
     * @param viewWidth
     * @param viewHeight
     * @param videoWidth
     * @param videoHeight
     */
    private void adjustAspectRatio(int viewWidth, int viewHeight, int videoWidth, int videoHeight) {
        final double aspectRatio = (double) videoHeight / videoWidth;
        int newWidth, newHeight;

        if (viewHeight > (int) (viewWidth * aspectRatio)) {
            // limited by narrow width; restrict height
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * aspectRatio);
        } else {
            // limited by short height; restrict width
            newWidth = (int) (viewHeight / aspectRatio);
            newHeight = viewHeight;
        }

        final int xoff = (viewWidth - newWidth) / 2;
        final int yoff = (viewHeight - newHeight) / 2;

        Log.d(TAG, "adjustAspectRatio: " + newWidth + "---" + newHeight);

        final Matrix txform = new Matrix();
        mTextureView.getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        txform.postTranslate(xoff, yoff);
        mTextureView.setTransform(txform);
    }

    /**
     * 播放器回调
     */
    public interface Callback {
        void onStarted(YiyaVideoPlayer player);

        void onPaused(YiyaVideoPlayer player);

        void onPreparing(YiyaVideoPlayer player);

        void onPrepared(YiyaVideoPlayer player);

        void onBuffering(int percent);

        void onInfo(MediaPlayer mp, int what, int extra);

        void onError(YiyaVideoPlayer player, Exception e);

        void onCompletion(YiyaVideoPlayer player);

        void onProgressUpdate(int currentPosition, int duration);

        void onSpecifiedPosition(ArrayList<Integer> positions);
    }

    private Callback mCallback;

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }
}
