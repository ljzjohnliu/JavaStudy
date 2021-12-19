package com.study.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
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

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
        background = typedArray.getColor(R.styleable.HeaderView_background, Color.WHITE);
        textColor = typedArray.getColor(R.styleable.HeaderView_titleColor, Color.BLACK);
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
    }

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
}
