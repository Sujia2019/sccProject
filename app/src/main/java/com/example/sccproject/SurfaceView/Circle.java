package com.example.sccproject.SurfaceView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circle extends Container {
    private Paint paint;
    public Circle() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
 
    }
 
    @Override
    public void childrenListView(Canvas canvas) {
        super.childrenListView(canvas);
        canvas.drawCircle(50,50,50,paint);
    }
}