package com.shawn.voicerecorderdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by cooffee on 15/10/19.
 */
public class RecorderLinearLayout extends LinearLayout {

    private static final String TAG = "RecorderLinearLayout";

    private static final int DISTANCE_CANCEL = 50;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;

    private int mCurState = STATE_NORMAL;

    private RecordingDialog mRecordingDialog;

    public RecorderLinearLayout(Context context) {
        this(context, null);
    }

    public RecorderLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecorderLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRecordingDialog = new RecordingDialog(getContext());
    }

    /**
     * 录音完成后的回调
     */
    public interface OnRecordListener {
        void onStart();

        void onFinish();
    }

    private OnRecordListener mListener;

    public void setOnRecordListener(OnRecordListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //                Log.i(TAG, "onTouchEvent: ACTION_DOWN");
                mRecordingDialog.show();
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                //                Log.i(TAG, "onTouchEvent: ACTION_MOVE");
                // 根据x, y的坐标，判断是否想要取消
                if (wantToCancel(x, y)) {
                    changeState(STATE_WANT_TO_CANCEL);
                } else {
                    changeState(STATE_RECORDING);
                }
                break;
            case MotionEvent.ACTION_UP:
                //                Log.i(TAG, "onTouchEvent: ACTION_UP");
                mRecordingDialog.dismiss();
                if (mListener != null) mListener.onFinish();
                changeState(STATE_NORMAL);
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -DISTANCE_CANCEL || y > getHeight() + DISTANCE_CANCEL) {
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
            switch (mCurState) {
                case STATE_NORMAL:
                    //                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    //                    setText(R.string.str_recorder_normal);
                    //                    Log.i(TAG, "changeState: STATE_NORMAL");
                    break;
                case STATE_RECORDING:
                    //                    setBackgroundResource(R.drawable.btn_recorder_recording);
                    //                    setText(R.string.str_recorder_recording);
                    mRecordingDialog.recording();
                    if (mListener != null) mListener.onStart();
                    //                    Log.i(TAG, "changeState: STATE_RECORDING");
                    break;
                case STATE_WANT_TO_CANCEL:
                    //                    setBackgroundResource(R.drawable.btn_recorder_recording);
                    //                    setText(R.string.str_recorder_want_cancel);
                    //                    Log.i(TAG, "changeState: STATE_WANT_TO_CANCEL");
                    mRecordingDialog.wantToCancel();
                    if (mListener != null) mListener.onFinish();
                    break;
            }
        }
    }

    public void updateVolume(int level) {
        mRecordingDialog.updateVoiceLevel(level);
    }

}
