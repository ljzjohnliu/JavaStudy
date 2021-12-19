package com.study.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.android.R;

/**
 *
 */
public class HeaderView extends RelativeLayout {

    private static final String TAG = "HeaderView";

    ImageView imgLeft;
    ImageView imgRight;
    TextView textCenter;
    RelativeLayout layoutRoot;

    private int background;
    private int textColor;
    private String title;
    private int showView;

    /* -------------------------------以下是对外提供的接口----------------------------- */
    //设置标题文字的方法
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            textCenter.setText(title);
        }
    }

    //对左边按钮设置事件的方法
    public void setLeftListener(OnClickListener onClickListener) {
        imgLeft.setOnClickListener(onClickListener);
    }

    //对右边按钮设置事件的方法
    public void setRightListener(OnClickListener onClickListener) {
        imgRight.setOnClickListener(onClickListener);
    }
    /* -------------------------------以上是对外提供的接口----------------------------- */

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
        background = typedArray.getColor(R.styleable.HeaderView_background, Color.WHITE);
        textColor = typedArray.getColor(R.styleable.HeaderView_titleColor, Color.BLACK);
        title = typedArray.getString(R.styleable.HeaderView_titleText);
        //获取show_views属性，如果没有设置时默认为0x26
        showView = typedArray.getInt(R.styleable.HeaderView_showViews, 0x32);
        typedArray.recycle();
        Log.d(TAG, "HeaderView: background = " + background + ", textColor = " + textColor);
        initView(context);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //初始化UI，可根据业务需求设置默认值。
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
        imgLeft = findViewById(R.id.header_left_img);
        imgRight = findViewById(R.id.header_right_img);
        textCenter = findViewById(R.id.header_center_text);
        layoutRoot = findViewById(R.id.header_root_layout);
        layoutRoot.setBackgroundColor(background);
        textCenter.setTextColor(textColor);
        if (!TextUtils.isEmpty(title)) {
            textCenter.setText(title);
        }
        showView(showView);
    }

    private void showView(int showView) {
        //将showView转换为二进制数，根据不同位置上的值设置对应View的显示或者隐藏。
        Long data = Long.valueOf(Integer.toBinaryString(showView));
        String element = String.format("%06d", data);
        Log.d(TAG, "showView: element = " + element);
        for (int i = 0; i < element.length(); i++) {
            Log.d(TAG, "showView: i = " + i + ", element is = " + element.charAt(i));
            if (i == 0) ;
            if (i == 1) {
                Log.d(TAG, "showView: position 1 is " + element.charAt(i));
                textCenter.setVisibility(element.charAt(i) == '1' ? View.VISIBLE : View.GONE);
            }
            if (i == 2) {
                Log.d(TAG, "showView: position 2 is " + element.charAt(i));
                imgRight.setVisibility(element.charAt(i) == '1' ? View.VISIBLE : View.GONE);
            }
            if (i == 3) ;
            if (i == 4) {
                Log.d(TAG, "showView: position 4 is " + element.charAt(i));
                imgLeft.setVisibility(element.charAt(i) == '1' ? View.VISIBLE : View.GONE);
            }
            if (i == 5) ;
        }

    }
}
