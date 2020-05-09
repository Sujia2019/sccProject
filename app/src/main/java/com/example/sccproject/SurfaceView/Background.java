package com.example.sccproject.SurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/*
 * 无缝背景滚动效果
 */
public class Background {

    private Bitmap bmp;
    private int x1, x2;
    private int y1, y2;
    private int speed;
    private int widthScreen, heightScreen;
    private int widthBitmap, heigthtBitmap;

    public Background(Bitmap bmp, int speed, int widthScreen, int heightScreen) {
        this.bmp = bmp;
        widthBitmap = bmp.getWidth();
        heigthtBitmap = bmp.getHeight();
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = y1+heigthtBitmap;
        this.speed = speed;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
    }
    /**
     * 贴图逻辑
     * y1初始从坐标0 开始贴图, 慢慢往下移动, y2则补齐y1 往下移动留下的空白.
     * 当y1 移除到界面时,y1 此时应该从y2 位置的开始不能从0开始, y2继续补齐y1 留下的空白
     */
    public void logic() {
        //	y1 = y1 > heightScreen ? 0 : y1+speed;
        y1 = y1 > heightScreen ? y2 : y1+speed;
        y2 = y1- heigthtBitmap;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmp, x1, y1, null);
        canvas.drawBitmap(bmp, x2, y2, null);
    }
}