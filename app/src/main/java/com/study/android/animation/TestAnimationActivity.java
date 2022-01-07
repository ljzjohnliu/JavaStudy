package com.study.android.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestAnimationActivity extends BaseSimpleActivity {
    private static final String TAG = "TestAnimationAc";

    private Context mContext;
    private AnimationDrawable animationDrawable1;

    @BindView(R.id.anim_img1)
    ImageView animImg1;
    @BindView(R.id.anim_img2)
    ImageView animImg2;
    @BindView(R.id.anim_img3)
    ImageView animImg3;

    @OnClick({R.id.frame_anim, R.id.tween_anim, R.id.animator_anim, R.id.animator_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frame_anim:
                if (animationDrawable1 != null && animationDrawable1.isRunning()) {
                    animationDrawable1.stop();
                } else {
                    animationDrawable1.start();
                }
                break;
            case R.id.tween_anim:
                translationAnimation();
//                rotateAnimation();
//                alphaAnimation();
//                scaleAnimation();
                break;
            case R.id.animator_anim:
//                rotateObjAnimation();
//                alphaObjAnimation();
                objAnimationSet();
                break;
            case R.id.animator_my:
//                myObjAnimation();
//                myObjAnimationAsXml();
                myObjAnimationSetAsXml();
                break;
        }
    }

    @OnClick({R.id.anim_img2, R.id.anim_img3})
    public void onImgClick(View view) {
        switch (view.getId()) {
            case R.id.anim_img2:
                Log.d(TAG, "onImgClick: 2222 anim_img2 is clicked!!!");
                break;
            case R.id.anim_img3:
                Log.d(TAG, "onImgClick: 3333 anim_img3 is clicked!!!");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);
        ButterKnife.bind(this);
        mContext = this;
        Log.d(TAG, "onCreate: " + Utils.getPids());
        animImg1.setImageResource(R.drawable.frame_anim1);
        animationDrawable1 = (AnimationDrawable) animImg1.getDrawable();
    }

    /* ----------------------属性动画---------------------- */
    private void rotateObjAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(animImg3, "rotation", 0f, 360f);
        anim.setDuration(1000);
        anim.start();
    }

    private void alphaObjAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(animImg3, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f);
        anim.setRepeatCount(-1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(2000);
        anim.start();
    }

    private void objAnimationSet() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(animImg3, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(animImg3, "scaleX", 0.0f, 1.5f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(animImg3, "scaleY", 0.0f, 1.5f);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(animImg3, "rotation", 0, 360);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(animImg3, "translationX", 50, 100);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(animImg3, "translationY", 100, 250);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
//                set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set.setDuration(3000);
        set.start();
    }

    private void myObjAnimation() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(animImg3, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(animImg3, "scaleY", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        // 设置插值器
        animatorSet.setInterpolator(new MyTimeInterpolator());
//        scaleXAnimator.setRepeatCount(-1);
//        scaleYAnimator.setRepeatCount(-1);
        animatorSet.setDuration(3000);
        animatorSet.start();
    }

    private void myObjAnimationAsXml() {
        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.my_obj_animator);
        animator.setTarget(animImg3);
        animator.start();
    }

    private void myObjAnimationSetAsXml() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.my_obj_animator_set);
        set.setTarget(animImg3);
        set.start();
    }

    /* ----------------------补间动画---------------------- */
    private Animation animation;
    float deValue = 360 * 1 / 100.0f;
    float pxValue = 0;
    float pyValue = 0;

    private void translationAnimation() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.translate_anim);
        animation.setInterpolator(new LinearInterpolator());

        animation.setFillAfter(true);
//        animation.setRepeatCount(-1);
//        animation.setRepeatMode(Animation.REVERSE);
//        animation.setRepeatMode(Animation.RESTART);
        animImg2.startAnimation(animation);
    }

    /**
     * rotate Animation
     */
    private void rotateAnimation() {
        animation = new RotateAnimation(-deValue, deValue, Animation.RELATIVE_TO_SELF,
                pxValue, Animation.RELATIVE_TO_SELF, pyValue);
        animation.setDuration(3000);

        animation.setFillAfter(true);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
//        animation.setRepeatMode(Animation.RESTART);
        animImg2.startAnimation(animation);
        animImg2.startAnimation(animation);
    }

    /**
     * alpha Animation
     */
    private void alphaAnimation() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);
        animation.setFillAfter(true);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
//        animation.setRepeatMode(Animation.RESTART);
        animImg2.startAnimation(animation);
        animImg2.startAnimation(animation);
    }

    /**
     * scale Animation
     */
    private void scaleAnimation() {
//        animation = null;
        animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_anim1);
//        animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_anim2);
//        animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_anim3);

        animation.setFillAfter(true);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
//        animation.setRepeatMode(Animation.RESTART);
        animImg2.startAnimation(animation);
        animImg2.startAnimation(animation);
    }
}
