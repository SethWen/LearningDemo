package com.shawn.voicerecorderdemo;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cooffee on 15/10/19.
 */
public class RecordingDialog extends Dialog {

    private ImageView mIcon;
    private ImageView mVoice;

    private TextView mLabel;


    public RecordingDialog(Context context) {
        super(context, R.style.AudioDialogTheme);
        setContentView(R.layout.dialog_recorder);

        mIcon = (ImageView) findViewById(R.id.id_recorder_dialog_icon);
        mVoice = (ImageView) findViewById(R.id.id_recorder_dialog_voice);
        mLabel = (TextView) findViewById(R.id.id_recorder_dialog_label);
    }

    public void recording() {
        mIcon.setVisibility(View.VISIBLE);
        mVoice.setVisibility(View.VISIBLE);
        mLabel.setVisibility(View.VISIBLE);

        mIcon.setImageResource(R.drawable.recorder);
        mLabel.setText("手指上滑，取消发送");
    }

    public void wantToCancel() {
        mIcon.setVisibility(View.VISIBLE);
        mVoice.setVisibility(View.GONE);
        mLabel.setVisibility(View.VISIBLE);

        mIcon.setImageResource(R.drawable.cancel);
        mLabel.setText("松开手指，取消发送");
    }

    public void tooShort() {
        mIcon.setVisibility(View.VISIBLE);
        mVoice.setVisibility(View.GONE);
        mLabel.setVisibility(View.VISIBLE);

        mIcon.setImageResource(R.drawable.voice_to_short);
        mLabel.setText("录音时间过短");
    }

//    public void dimissDialog() {
//    }

    /**
     * 通过更新level来更新voice上的图片
     *
     * @param level 1~7
     */
    public void updateVoiceLevel(int level) {
        //            mIcon.setVisibility(View.VISIBLE);
        //            mVoice.setVisibility(View.VISIBLE);
        //            mLabel.setVisibility(View.VISIBLE);

        int resId = getContext().getResources().getIdentifier("v" + level, "drawable",
                getContext().getPackageName());
        mVoice.setImageResource(resId);
    }
}
