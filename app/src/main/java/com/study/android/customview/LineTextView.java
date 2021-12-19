package com.study.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.study.android.R;

public class LineTextView extends AppCompatTextView {

    //定义画笔，用来绘制中心曲线
    private Paint mPaint;
    private int lineColor;

    public LineTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public LineTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineTextView);
        lineColor = typedArray.getInt(R.styleable.LineTextView_lineColor, Color.BLACK);
        typedArray.recycle();
        init();
    }

    public LineTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    /**
     * 重写draw方法，绘制我们需要的中间线以及背景
     * 这里需要注意super方法的调用时机，先绘制背景色，再调用super绘制文字，最后绘制中间线！
     */
    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
        mPaint.setColor(Color.DKGRAY);
        //绘制方形背景
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRect(rectF, mPaint);

        super.onDraw(canvas);

        //绘制中心曲线，起点坐标（0,height/2），终点坐标（width,height/2）
        mPaint.setColor(lineColor);
        mPaint.setTextSize(10f);
        canvas.drawLine(0, height / 2, width, height * 3 / 5, mPaint);
    }
}
