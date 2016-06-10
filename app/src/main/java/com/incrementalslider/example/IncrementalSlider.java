package com.incrementalslider.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Seungyong Yun on 16. 6. 9.
 */
public class IncrementalSlider extends View {


    private static final float TOLERANCE = 50;
    private int[] mColors;
    private float mDividerWidth;
    private Paint mGaugePaint;
    private Path mDrawingPath;
    private int mGaugeBackgroundColor;
    private int mLevel;
    private Paint mLabelPaint;
    private String mLeftLabelText;
    private String mRightLabelText;

    public IncrementalSlider(Context context) {
        this(context, null);
    }

    public IncrementalSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncrementalSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public IncrementalSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        mColors = new int[]{0xFFFF87B8, 0xFFFF4591, 0xFFFF0068};
        mDividerWidth = 5.0f;
        mGaugePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGaugePaint.setStyle(Paint.Style.FILL);
        mDrawingPath = new Path();
        mGaugeBackgroundColor = 0x30000000;
        mLevel = 2;

        mLeftLabelText = "Empty";
        mRightLabelText = "Full";

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setColor(0xFF6D7E92);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int colorSize = mColors.length;
        float labelLineHeight = getLineHeight(mLabelPaint);
        float formYOffset = (labelLineHeight - calcTextHeight(mLabelPaint, "ABC")) / 2.f;
        float gaugeHeight = getHeight() - labelLineHeight;
        float slope = gaugeHeight / (float) getWidth();
        float unitWidth = ((float) getWidth() - ((colorSize - 1) * mDividerWidth)) / (float) mColors.length;

        for (int i = 0; i < colorSize; i++) {
            mGaugePaint.setColor(i < mLevel ? mColors[i] : mGaugeBackgroundColor);
            mDrawingPath.reset();
            mDrawingPath.moveTo(i * (unitWidth + mDividerWidth), gaugeHeight);
            mDrawingPath.rLineTo(unitWidth, 0);
            mDrawingPath.rLineTo(0, -slope * ((i * (unitWidth + mDividerWidth)) + unitWidth));
            mDrawingPath.rLineTo(-unitWidth, slope * unitWidth);
            canvas.drawPath(mDrawingPath, mGaugePaint);
        }

        mLabelPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mLeftLabelText, 0, getHeight() - formYOffset, mLabelPaint);

        mLabelPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(mRightLabelText, getWidth(), getHeight() - formYOffset, mLabelPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final int action = event.getAction();

        int colorSize = mColors.length;
        float unitWidth = ((float) getWidth() - ((colorSize - 1) * mDividerWidth)) / (float) mColors.length;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int prevLevel = mLevel;
                if (x <= unitWidth / 3.0f) {
                    mLevel = 0;
                } else if (x <= unitWidth + mDividerWidth) {
                    mLevel = 1;
                } else if (x <= (2 * (unitWidth + mDividerWidth))) {
                    mLevel = 2;
                } else {
                    mLevel = 3;
                }

                if (prevLevel != mLevel) {
                    invalidate();
                }
                break;
        }

        return true;
    }

    public int getLevel() {
        return mLevel;
    }

    public static int calcTextWidth(Paint paint, String demoText) {
        return (int) paint.measureText(demoText);
    }

    public static int calcTextHeight(Paint paint, String demoText) {

        Rect r = new Rect();
        paint.getTextBounds(demoText, 0, demoText.length(), r);
        return r.height();
    }

    public static float getLineHeight(Paint paint) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return metrics.descent - metrics.ascent;
    }

    public void setLeftLabelText(String leftLabelText) {
        mLeftLabelText = leftLabelText;
        invalidate();
    }

    public void setRightLabelText(String rightLabelText) {
        mRightLabelText = rightLabelText;
        invalidate();
    }

    public void setLabelTextSize(float labelTextSize) {
        mLabelPaint.setTextSize(labelTextSize);
        invalidate();
    }

    public void setColors(int[] colors) {
        mColors = colors;
        invalidate();
    }

    public void setDividerWidth(float dividerWidth) {
        mDividerWidth = dividerWidth;
        invalidate();
    }

    public void setLevel(int level) {
        mLevel = level;
        invalidate();
    }

    public void setGaugeBackgroundColor(int gaugeBackgroundColor) {
        mGaugeBackgroundColor = gaugeBackgroundColor;
    }
}
