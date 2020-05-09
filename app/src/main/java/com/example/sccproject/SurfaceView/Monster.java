package com.example.sccproject.SurfaceView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.sccproject.R;

public class Monster extends Container {
    private Bitmap[] mBitmaps;
    private int index = 0;
    private Resources res;
    public Monster() {
        mBitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(res,R.drawable.bat_idle1),
                BitmapFactory.decodeResource(res,R.drawable.bat_idle2),
                BitmapFactory.decodeResource(res,R.drawable.bat_idle3),
                BitmapFactory.decodeResource(res,R.drawable.bat_idle4)
        };
    }

    @Override
    public void childrenListView(Canvas canvas) {
        super.childrenListView(canvas);
        canvas.drawBitmap(mBitmaps[index],0,0,null);
    }}
