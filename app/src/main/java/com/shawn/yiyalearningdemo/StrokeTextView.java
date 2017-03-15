package com.shawn.yiyalearningdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * author: Shawn
 * time  : 2016/12/23 11:51
 */

public class StrokeTextView extends TextView {

    private TextView storekeText;        // 用于描边的TextView
    private TextPaint tp;
    private float strokeWidth;          // 描边宽度
    private int strokeColor;            // 描边颜色
    private boolean isPinyin;

    private static final int DEFAULT_STROKE_COLOR = 0xFF0a0f5a;
    private static final int DEFAULT_STROKE_WIDTH = 6;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        storekeText = new TextView(context, attrs, defStyle);
        tp = storekeText.getPaint();

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StrokeTextView, 0, 0);
            try {
                strokeWidth = typedArray.getDimension(R.styleable.StrokeTextView_textStrokeWidth, DEFAULT_STROKE_WIDTH);
                strokeColor = typedArray.getColor(R.styleable.StrokeTextView_textStrokeColor, DEFAULT_STROKE_COLOR);
                isPinyin = typedArray.getBoolean(R.styleable.StrokeTextView_textIsPinyin, false);
            } finally {
                typedArray.recycle();
            }
        }

        init();
    }

    public void init() {
        // 处理拼音字体
        if (isPinyin) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/TTXiHeiJ.ttf");
            this.setTypeface(typeface);
            storekeText.setTypeface(typeface);
        }
        storekeText.setGravity(getGravity());
        storekeText.setTextColor(strokeColor);              // 设置描边颜色
        tp.setStyle(Style.STROKE);                          // 对文字只描边
        tp.setStrokeWidth(strokeWidth);                     // 设置描边宽度
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        storekeText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence cs = storekeText.getText();

        // 两个TextView上的内容必须一致
        if (cs == null || !cs.equals(this.getText())) {
            storekeText.setText(getText());
            this.postInvalidate();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        storekeText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        storekeText.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        storekeText.draw(canvas);
        super.onDraw(canvas);
    }

    public void setStrokeWidth(float strokeWidth) {
        if (this.strokeWidth == strokeWidth) {
            return;
        }
        this.strokeWidth = strokeWidth;
        tp.setStrokeWidth(strokeWidth);
        this.postInvalidate();
    }

    public void setStrokeColor(int strokeColor) {
        if (this.strokeColor == strokeColor) {
            return;
        }
        this.strokeColor = strokeColor;
        storekeText.setTextColor(strokeColor);
        this.postInvalidate();
    }

    public void setPinyin(boolean pinyin) {
        if (this.isPinyin == pinyin) {
            return;
        }
        this.isPinyin = pinyin;
        if (isPinyin) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/TTXiHeiJ.ttf");
            this.setTypeface(typeface);
            storekeText.setTypeface(typeface);
        }
        this.postInvalidate();
    }
}