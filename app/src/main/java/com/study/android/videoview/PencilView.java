package com.study.android.videoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PencilView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "PencilView";
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    private boolean mIsRunning;

    public PencilView(Context context) {
        this(context, null);
    }

    public PencilView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PencilView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: holder = " + holder);
        mIsRunning = true;
        new Thread(this).start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceCreated: holder = " + holder + ", format = " + format + ", width = " + width + ", height = " + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsRunning = false;
        Log.d(TAG, "surfaceDestroyed: holder = " + holder);
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        while (mIsRunning) {
            draw();
        }
    }

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);//设置画笔颜色
        mPaint.setStrokeJoin(Paint.Join.ROUND);//设置多次调用 Path.lineTo 这种线段之间连接处的样式
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的线冒样式
        mPaint.setStrokeWidth(26f);
        if (mCanvas != null) {
            try {
                //使用获得的Canvas做具体的绘制
                mCanvas.drawCircle(100, 100, 90, mPaint);
                mPaint.setColor(Color.YELLOW);
                mCanvas.drawLine(200, 30, 400, 30, mPaint);
//                mCanvas.drawColor(Color.BLUE);//整个区域填充蓝色
                RectF rect = new RectF(420, 30, 620, 230);
                mPaint.setColor(Color.BLUE);
                mCanvas.drawRoundRect(rect, 20, 20, mPaint);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
