package com.shawn.yiyalearningdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.content.ContentValues.TAG;

/**
 * Created by hedge_hog on 2015/06/11.
 * update by hedge_hog on 2016/08/06
 */
public class RatingBar extends LinearLayout {
    private int starCount;
    private float rating;
    private float starImageSize;
    private float starImageWidth;
    private float starImageHeight;
    private float starImageHorizontalMargin;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;
    private boolean isEmpty = true;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        starHalfDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starHalf);
        starEmptyDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starEmpty);
        starFillDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starFill);
        starImageSize = mTypedArray.getDimension(R.styleable.RatingBar_starImageSize, 120);
        starImageWidth = mTypedArray.getDimension(R.styleable.RatingBar_starImageWidth, 60);
        starImageHeight = mTypedArray.getDimension(R.styleable.RatingBar_starImageHeight, 120);
        starImageHorizontalMargin = mTypedArray.getDimension(R.styleable.RatingBar_starImageHorizontalMargin, 15);
        starCount = mTypedArray.getInteger(R.styleable.RatingBar_starCount, 5);
        rating = mTypedArray.getFloat(R.styleable.RatingBar_rating, 0);
        mTypedArray.recycle();

        for (int i = 0; i < starCount; ++i) {
            ImageView imageView = getStarImageView(context, i, isEmpty);
            addView(imageView);
        }

        setRating(rating);
    }

    public void setStarHalfDrawable(Drawable starHalfDrawable) {
        this.starHalfDrawable = starHalfDrawable;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setStarImageWidth(float starImageWidth) {
        this.starImageWidth = starImageWidth;
    }

    public void setStarImageHeight(float starImageHeight) {
        this.starImageHeight = starImageHeight;
    }

    public void setStarCount(int starCount) {
        if (starCount <= 0) {
            this.starCount = 5;
            return;
        }
        this.starCount = starCount;
        invalidate();
    }

    public void setImagePadding(float starImagePadding) {
        this.starImageHorizontalMargin = starImagePadding;
    }

    private ImageView getStarImageView(Context context, int i, boolean isEmpty) {
        ImageView imageView = new ImageView(context);
        LayoutParams params = new LinearLayout.LayoutParams(
                Math.round(starImageWidth),
                Math.round(starImageHeight)
        );
        if (i != 0) {
            params.leftMargin = Math.round(starImageHorizontalMargin);
        }
        imageView.setLayoutParams(params);

        if (isEmpty) {
            imageView.setImageDrawable(starEmptyDrawable);
        } else {
            imageView.setImageDrawable(starFillDrawable);
        }
        return imageView;
    }

    public void setRating(float rating) {

        // eg:
        // rating = 3.2 -> draw 3 stars
        // rating = 3.5 -> draw 3.5 stars
        // rating = 3.8 -> draw 4 stars

        // 修正 rating 数值
        if (rating > starCount) rating = starCount; // 3
        if (rating < 0) rating = 0;

        int floor = (int) Math.floor(rating); // 3
        int ceil = (int) Math.ceil(rating);   // 4

        Log.e(TAG, "setRating: floor = " + floor);
        Log.e(TAG, "setRating: ceil = " + ceil);

        for (int i = 0; i < floor; i++) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
        }
        for (int i = ceil; i < starCount; i++) {
            ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
        }

        double dx = rating - floor;
        if (dx >= 0.25 && dx <= 0.75) {
            // 设置半星
            setSpeStar(floor, starHalfDrawable);
        } else if (dx >= 0 && dx < 0.25) {
            if (floor == starCount) {
                // 如果 rating==starCount，那么最后一个星星为全星
                ((ImageView) getChildAt(floor - 1)).setImageDrawable(starFillDrawable);
            } else {
                // 设置空星
                ((ImageView) getChildAt(floor)).setImageDrawable(starEmptyDrawable);
            }
        } else {
            // 设置全星
            setSpeStar(floor, starFillDrawable);
        }
    }

    /**
     * 设置 rating 值 小数点后数值对应的星星
     *
     * @param floor
     * @param starDrawable
     */
    private void setSpeStar(int floor, Drawable starDrawable) {
        if (floor == starCount) {
            ((ImageView) getChildAt(floor - 1)).setImageDrawable(starDrawable);
        } else {
            ((ImageView) getChildAt(floor)).setImageDrawable(starDrawable);
        }
    }

}
