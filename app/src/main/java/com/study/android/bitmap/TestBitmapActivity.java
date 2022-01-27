package com.study.android.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.DisplayUtil;
import com.study.android.utils.SystemUIUtils;
import com.study.android.utils.Utils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestBitmapActivity extends BaseSimpleActivity {
    private static final String TAG = "TestBitmapAc";

    private Context mContext;
    private int screenWidth;
    private int screenHeight;

    @BindView(R.id.anim_img1)
    ImageView animImg1;
    @BindView(R.id.anim_img2)
    ImageView animImg2;
    @BindView(R.id.anim_img3)
    ImageView animImg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bitmap);
        ButterKnife.bind(this);
        mContext = this;
        screenWidth = SystemUIUtils.getScreenWidth(this);
        screenHeight = SystemUIUtils.getScreenHeight(this);
        Log.d(TAG, "onCreate, Display density = " + DisplayUtil.getDisplayDensity(this)
                + ", screen width = " + screenWidth + ", screen height = " + screenHeight);
        Log.d(TAG, "onCreate: ScreenDensities = " + Arrays.toString(SystemUIUtils.getScreenDensities(this)));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen);
        Log.d(TAG, "onCreate: bitmap size = " + bitmap.getByteCount() / 1024);//KB
        animImg1.setImageBitmap(bitmap);

        Bitmap hBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen_h);
        Log.d(TAG, "onCreate: hBitmap size = " + hBitmap.getByteCount() / 1024);//KB
//        animImg2.setImageBitmap(hBitmap);

        Bitmap xxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen_xx);
        Log.d(TAG, "onCreate: xxBitmap size = " + xxBitmap.getByteCount() / 1024);//KB
        animImg2.setImageBitmap(xxBitmap);

        Bitmap xxxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen_xxx);
        Log.d(TAG, "onCreate: xxxBitmap size = " + xxxBitmap.getByteCount() / 1024);//KB
        animImg3.setImageBitmap(xxxBitmap);
        xxxBitmap.recycle();

        getSmallBitmap("res://drawable/" + R.drawable.queen_xx, screenWidth, screenHeight);
    }

    public Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();//获取Bitmap工厂类
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //避免出现内存溢出的情况，进行相应的属性设置。
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;

        return BitmapFactory.decodeFile(filePath, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int outWith, int outHeight) {
        options.inJustDecodeBounds = true; //true 只返回Bitmap宽高 false 返回Bitmap对象实体
        int width = options.outWidth;
        int height = options.outHeight;
        options.inPurgeable = true;
        int inSampleSize = 1; //采样率:采样率以1/2++ 形式换算
        Log.d(TAG, "calculateInSampleSize: width = " + width);
        //根据输入的宽高换算采样率
        if (height > outHeight || width > outWith) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= outHeight &&
                    (halfWidth / inSampleSize) >= outWith) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "calculateInSampleSize inSampleSize = " + inSampleSize);
        return inSampleSize;
    }
}
