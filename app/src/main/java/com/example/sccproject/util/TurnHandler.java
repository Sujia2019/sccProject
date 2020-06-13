package com.example.sccproject.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class TurnHandler {
    public static Bitmap turn(Resources resources,int imageId) {
        //获取资源图片
        Bitmap bm =  BitmapFactory.decodeResource(resources,imageId);
        //处理
        Bitmap modBm = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(),bm.getConfig());
        //处理画布  重新绘制图形  形成镜像
        Canvas canvas = new Canvas(modBm);
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        //镜子效果：
        matrix.setScale(-1,1);//翻转
        matrix.postTranslate(bm.getWidth(),0);
        canvas.drawBitmap(bm,matrix,paint);
        return modBm;
    }
}
