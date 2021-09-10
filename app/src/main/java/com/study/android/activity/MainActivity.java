package com.study.android.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.communicate.RemoteActivity;
import com.study.android.utils.ProcessUtil;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_FROM = "KEY_FROM";
    public static final int RESULT_FROM_REMOTE = 1001;

    private TextView titleTv;

    private ActivityResultLauncher myActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_FROM_REMOTE) {
            String name = result.getData().getStringExtra(KEY_FROM);
            titleTv.setText("来自Remote的value:" + name);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTv = findViewById(R.id.title);
        titleTv.setText(ProcessUtil.getCurrentProcessName());

        /**
         * 匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加容易维护。
         * 代码中需要给按钮设置监听器对象，使用匿名内部类能够在实现父类或者接口中的方法情况下同时产生一个相应的对象，
         *
         * 匿名内部类也是不能有访问修饰符和static修饰符的。
         *
         * 匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，大部分匿名内部类用于接口回调。
         * 匿名内部类在编译的时候由系统自动起名为Outter$1.class。
         * 一般来说，匿名内部类用于继承其他类或是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是重写。
         */
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //D TAG     : onClick: title!!! this = com.study.android.activity.MainActivity$1@58f606c
                Log.d("TAG", "onClick: title!!! this = " + this);
                Intent mainIntent = new Intent(MainActivity.this, RemoteActivity.class);
                mainIntent.putExtra(KEY_FROM, "main process");
                myActivityLauncher.launch(mainIntent);
            }
        });
    }
}