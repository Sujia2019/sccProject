package com.example.sccproject.SurfaceView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Rect extends Container {
    private Paint paint;
    public Rect() {
        paint = new Paint();
        paint.setColor(Color.RED);
    }
 
    @Override
    public void childrenListView(Canvas canvas) {
        super.childrenListView(canvas);
        canvas.drawRect(0,0,100,100,paint);
        this.setY(this.getY()+1);
    }
}