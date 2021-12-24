package com.study.android.videoview;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.study.android.R;

public class AnimationSurfaceView extends SurfaceView {
    private SurfaceHolder surfaceHolder;
    private boolean flag = false;// 线程标识
    private Bitmap bitmap_bg;// 背景图

    private float mSurfaceWidth, mSurfaceHeight;// 屏幕宽高
    private int mBitposX;//开始绘制的图片的X坐标
    private Canvas mCanvas;
    private Thread thread;

    // 背景移动状态
    private enum State {
        LEFT, RIGHT
    }

    // 默认为向左
    private State state = State.LEFT;

    private final int BITMAP_STEP = 10;// 背景画布移动步伐.

    public AnimationSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                flag = true;
                startAnimation();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                flag = false;
            }
        });
    }

    private void startAnimation() {
        mSurfaceWidth = getWidth();
        mSurfaceHeight = getHeight();
        int width = (int) (mSurfaceWidth * 5 / 4);
        /***
         * 将图片缩放到屏幕的3/2倍.
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.login_bg);
        bitmap_bg = Bitmap.createScaledBitmap(bitmap, width, (int) mSurfaceHeight, true);

        //开始绘图
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    mCanvas = surfaceHolder.lockCanvas();
                    DrawView();
                    surfaceHolder.unlockCanvasAndPost(mCanvas);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    /***
     * 进行绘制.
     */
    protected void DrawView() {
        //绘制背景
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清屏幕.
        mCanvas.drawBitmap(bitmap_bg, mBitposX, 0, null);// 绘制当前屏幕背景

        /** 图片滚动效果 **/
        switch (state) {
            case LEFT:
                mBitposX -= BITMAP_STEP;// 画布左移
                break;
            case RIGHT:
                mBitposX += BITMAP_STEP;// 画布右移
                break;

            default:
                break;
        }
        if (mBitposX <= -mSurfaceWidth / 4) {
            state = State.RIGHT;
        }
        if (mBitposX >= 0) {
            state = State.LEFT;
        }
    }
}
