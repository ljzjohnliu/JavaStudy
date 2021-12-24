package com.study.android.videoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.study.android.R;
import com.study.android.utils.DisplayUtil;

public class DealBitmapView extends View {
    private static final String TAG = "TestView";

    private String textStr;

    public DealBitmapView(Context context) {
        this(context, null);
    }

    public DealBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.test);
        textStr = ta.getString(R.styleable.test_text);
        int textAttr = ta.getInteger(R.styleable.test_txtSize, -1);
        Log.d(TAG, "textStr = " + textStr + " , textAttr = " + textAttr);
        ta.recycle();
    }

    public DealBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DealBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    /**
     * 重写onMeasure方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.d(TAG, "onMeasure: widthMode = " + widthMode + ", heightMode = " + heightMode);
        Log.d(TAG, "onMeasure: widthMode AT_MOST = " + (widthMode == MeasureSpec.AT_MOST) + ", heightMode AT_MOST = " + (heightMode == MeasureSpec.AT_MOST));
        Log.d(TAG, "onMeasure: widthSize = " + widthSize + ", heightSize = " + heightSize);
        //处理wrap_content情况
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            Log.d(TAG, "onMeasure: ---------1111--------");
            setMeasuredDimension(300, 300);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Log.d(TAG, "onMeasure: ---------2222--------");
            setMeasuredDimension(1080, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            Log.d(TAG, "onMeasure: ---------3333--------");
            setMeasuredDimension(widthSize, 300);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6f);
        mPaint.setAlpha(180);

        //获取各个编剧的padding值
//        int paddingLeft = getPaddingLeft();
//        int paddingRight = DisplayUtil.px2dip(getContext(), getPaddingRight());
//        int paddingTop = getPaddingTop();
//        int paddingBottom = getPaddingBottom();
//        //获取绘制的View的宽度
//        int width = getWidth() - paddingLeft - paddingRight;
//        //获取绘制的View的高度
//        int height = getHeight() - paddingTop - paddingBottom;
//        //绘制View，左上角坐标（0+paddingLeft,0+paddingTop），右下角坐标（width+paddingLeft,height+paddingTop）
//        Log.d(TAG, "onDraw: getWidth = " + getWidth() + ", getHeight = " + getHeight() + ", width = " + width + ", height = " + height);
//        Log.d(TAG, "onDraw: paddingLeft = " + paddingLeft + ", paddingRight = " + paddingRight
//                + ", paddingTop = " + paddingTop + ", paddingBottom = " + paddingBottom);
//        canvas.drawRect(0 + paddingLeft, 0 + paddingTop, width + paddingLeft, height + paddingTop, mPaint);
//
//        drawTxt(canvas);

        RectF rect = new RectF(420, 30, 620, 230);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test);
        canvas.drawBitmap(bitmap, 20, 10, mPaint);
        canvas.translate(100, 50);
        canvas.rotate(30);
    }

    int textLength;
    private Paint.FontMetrics fontMetrics;
    Paint textPaint;

    private void drawTxt(Canvas canvas) {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.RED);
        // todo scaledDensity 字体缩放比例，即 单位sp的换算值。一般用在设定字体大小中。
        textPaint.setTextSize(getContext().getResources().getDisplayMetrics().scaledDensity * 20);
        String text = "abcdefg";

        // 1、粗略计算文字宽度
        textLength = (int) textPaint.measureText(text);
        Log.e(TAG, "粗略计算文字宽度:" + textLength);

        // 2、计算文字所在矩形，可以得到宽高
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        Log.e(TAG, "计算文字所在矩形，可以得到宽高:" + rect.width());

        //3、计算每一个字符的宽度
        if (text != null && text.length() > 0) {
            int len = text.length();
            float[] widths = new float[len];
            //得到字符的宽度
            textPaint.getTextWidths(text, widths);
            for (int j = 0; j < len; j++) {
                textLength += (int) Math.ceil(widths[j]);
            }
        }
        Log.e(TAG, "精确计算文字宽度  :" + textLength);

        fontMetrics = textPaint.getFontMetrics();
        int y = (int) (getHeight() / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2);
        int x = getPaddingLeft();
        canvas.drawText(text, x, y, textPaint);
    }
}
