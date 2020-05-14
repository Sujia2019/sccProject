package com.example.sccproject.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by alienware on 2020/4/10.
 */

public class AnimationUtils {

    /**
     *
     * @return
     */
    public static Animation getAlphaAnimation(){
        //实例化，透明度从 1-不透明  ， 0-完全透明
        Animation animation = new AlphaAnimation(1.0f,0.5f);
        //设置动画插值器，被用来修饰动画效果，定义动画的变化率
        animation.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间
        animation.setDuration(1000);
        return animation;
    }
    //移动
    public static Animation getTranAnimation(float x,float y){
        Animation animation = new TranslateAnimation(0,x,0,y);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(500);
        return animation;
    }

    /**
     * 缩放动画
     * @return
     */
    public static Animation getScaleAnimation(){
        //实例化 主要是缩放效果
        //参数，fromX-动画开始前，x坐标    toX-动画结束后x坐标
        //fromY-动画开始前，Y坐标  toY-动画结束后Y坐标
        //pivotXType - 为动画相对于物件的X坐标的参照物  pivotXValue - 值
        //piovtYType - 为动画相对于物件的Y坐标的参照物  pivotYValue - 值
        Animation animation = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        //设置动画插值器 被用来修饰动画效果，定义动画的变化率
        animation.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间
        animation.setDuration(1000);
        return animation;
    }

    /**
     * 旋转
     * @return
     */
    public static Animation getRotateAnimation(){
        //实例化RotateAnimation
        //以自身中心为圆心，旋转360度 正值为顺时针旋转，负值为逆时针旋转
        RotateAnimation animation = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        //设置动画插值器 被用来修饰动画效果，定义动画的变化率
        animation.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间
        animation.setDuration(1000);
        return animation;
    }




}
