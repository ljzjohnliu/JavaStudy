package com.study.android.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.study.android.R;

public class SelfView extends View {
    private static final String TAG = "SelfView";
    private final int DEFAULT_COLOR = Color.RED;
    private final int DEFAULT_SIZE = 320;
    private int color;
    private int length;
    private int textLength;
    private Paint textPaint;
    private Paint linePaint;
    private String text = "apPJHQjěǔ";

    private Paint.FontMetrics f;

    /**
     * 一般在直接New一个View的时候调用。
     */
    public SelfView(Context context) {
        this(context, null);
        Log.d(TAG, "SelfView: -----1111-----");
    }

    /**
     * 一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来。
     */
    public SelfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "SelfView: -----2222-----");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelfView);
        color = typedArray.getColor(R.styleable.SelfView_innerColor, DEFAULT_COLOR);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(color);
        // todo scaledDensity 字体缩放比例，即 单位sp的换算值。一般用在设定字体大小中。
        textPaint.setTextSize(context.getResources().getDisplayMetrics().scaledDensity * 20);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(1);
        length = (int) (context.getResources().getDisplayMetrics().density * DEFAULT_SIZE);

        f = textPaint.getFontMetrics();

        textLength = (int) textPaint.measureText(text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getWidthMeasureSpec(widthMeasureSpec), getHeightMeasureSpec(heightMeasureSpec));
    }

    private int getWidthMeasureSpec(int widthMeasureSpec) {

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int result = length;
        switch (specMode) {
            // Todo
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                specSize = textLength;
                Log.e("specSize", "specSize:" + specSize);
                result = Math.min(specSize, length);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result + getPaddingLeft() + getPaddingRight();

    }

    private int getHeightMeasureSpec(int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = length;
        switch (specMode) {
            // Todo
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                specSize = (int) (-f.top + f.bottom);
                result = Math.min(specSize, length);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result + getPaddingTop() + getPaddingBottom();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int x = getPaddingLeft();
        int y = (int) (getHeight() / 2 - (f.ascent + f.descent) / 2);
        Log.d("FontMetrics", "onDraw: x = " + x + ", y = " + y);
        Log.e("FontMetrics", "ascent:" + f.ascent + ",descent:" + f.descent + ",top:" + f.top + ",bottom:" + f.bottom);
        canvas.drawText(text, x, y, textPaint);
        //todo 画中心线
        linePaint.setColor(Color.YELLOW);
        canvas.drawLine(x, getHeight() / 2 - 0.5f, getWidth() - getPaddingRight(), getHeight() / 2 + 0.5f, linePaint);
        //todo baseline
        linePaint.setColor(Color.BLACK);
        canvas.drawLine(x, y - 0.5f, getWidth() - getPaddingRight(), y + 0.5f, linePaint);
        // todo ascent
        linePaint.setColor(Color.BLUE);
        canvas.drawLine(x, y - 0.5f + f.ascent, getWidth() - getPaddingRight(), y + 0.5f + f.ascent, linePaint);
        // todo descent
        linePaint.setColor(Color.GREEN);
        canvas.drawLine(x, y - 0.5f + f.descent, getWidth() - getPaddingRight(), y + 0.5f + f.descent, linePaint);
    }
}

