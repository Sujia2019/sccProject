package com.example.sccproject.SurfaceView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.sccproject.R;

/**
 *
 * @author Luv
 *
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sHolder;
    private Canvas canvas;
    private int widthScreen, heightScreen;
    private Thread thread;
    private boolean flag;

    private Background background;
    private Resources res;

    public MySurfaceView(Context context) {
        super(context);
        sHolder = getHolder();
        sHolder.addCallback(this);
        res = getResources();

    }

    public void draw() {
        try {
            canvas = sHolder.lockCanvas();
            //draw something
            background.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                sHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void logic() {
        background.logic();
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            //do something...or draw()
            logic();
            draw();
            long end = System.currentTimeMillis();
            long sleep = 50 - end + start;
            try {
                if (sleep>0) {
                    Thread.sleep(sleep);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        heightScreen = getHeight();
        widthScreen = getWidth();

        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.path_smile_start);
        background = new Background(bmp, 3, widthScreen, heightScreen);

        thread = new Thread(this);
        thread.start();
        flag = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        flag = false;
    }
}
