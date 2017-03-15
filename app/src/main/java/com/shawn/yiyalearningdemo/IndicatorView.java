package com.shawn.yiyalearningdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * author: Shawn
 * time  : 2016/12/12 15:51
 */

public class IndicatorView extends LinearLayout {

    private static final String TAG = "IndicatorView";

    private int indicatorCount;
    private float indicatorMargin;
    private Drawable selectedDrawable;
    private Drawable unselectedDrawable;
    private float indicatorRedius;
    private int selectedIndicator;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        indicatorCount = typedArray.getInteger(R.styleable.IndicatorView_indicatorCount, 3);
        indicatorMargin = typedArray.getDimension(R.styleable.IndicatorView_indicatorMargin, 20);
        selectedDrawable = typedArray.getDrawable(R.styleable.IndicatorView_slectedDrawable);
        unselectedDrawable = typedArray.getDrawable(R.styleable.IndicatorView_unselectedDrawable);
        indicatorRedius = typedArray.getDimension(R.styleable.IndicatorView_indicatorRedius, 16);
        selectedIndicator = typedArray.getInteger(R.styleable.IndicatorView_selectedIndicator, 0);
        typedArray.recycle();

        initIndicator(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initIndicator(Context context) {
        Log.i(TAG, "initIndicator: selectedIndicator=" + selectedIndicator);
        for (int i = 0; i < indicatorCount; i++) {
            ImageView indicator = new ImageView(context);
            if (i == selectedIndicator) {
                indicator.setImageDrawable(selectedDrawable);
            } else {
                indicator.setImageDrawable(unselectedDrawable);
            }
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.width = (int) indicatorRedius;
            params.height = (int) indicatorRedius;
            if (i != 0) {
                params.leftMargin = (int) indicatorMargin;
            }
            indicator.setLayoutParams(params);
            addView(indicator);
        }
    }

    public void setSelectedIndicator(int selectedIndicator) {
        // 修正数值
        if (this.selectedIndicator == selectedIndicator
                || selectedIndicator > indicatorCount - 1
                || selectedIndicator < 0)
            return;

        Log.i(TAG, "setSelectedIndicator: selectedIndicator＝" + selectedIndicator);
        Log.i(TAG, "setSelectedIndicator: this.selectedIndicator＝" + this.selectedIndicator);

        ((ImageView) getChildAt(selectedIndicator)).setImageDrawable(selectedDrawable);
        ((ImageView) getChildAt(this.selectedIndicator)).setImageDrawable(unselectedDrawable);
        this.selectedIndicator = selectedIndicator;
    }
}
