package com.example.sccproject.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class TurnHandler {
    //翻转
    public static Bitmap flipImage(int x,int y,Bitmap originalImage){
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1,-1);
        //创建一个应用了翻转矩阵的位图。
        //我们只想要图像的下半部分
        Bitmap flipImage = Bitmap.createBitmap(originalImage,x,y,width,height,matrix,true);
        //创建一个新的Canvas，其位图足够
        Canvas canvas = new Canvas(flipImage);
        //绘制图像
        canvas.drawBitmap(flipImage,0,0,null);
        return flipImage;
    }

    public static Bitmap turnToOther(int x,int y,Bitmap originalImage,int drawable){
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Bitmap other = BitmapFactory.decodeResource(Resources.getSystem(),drawable);
        int width2 = other.getWidth();
        int height2 = other.getHeight();

        //变换后x坐标
        x=x-Math.abs(width/2-width2/2/5*3);
        //变换后y坐标
        y=y-Math.abs(height-height2/5*3);
        Matrix matrix = new Matrix();
        matrix.setScale(0.6f,0.6f);

        Bitmap flipImage = Bitmap.createBitmap(other,x,y,width2,height2,matrix,true);
        //创建一个新的Canvas，其位图足够
        Canvas canvas = new Canvas(flipImage);
        //绘制图像
        canvas.drawBitmap(flipImage,0,0,null);
        return flipImage;
    }
}
