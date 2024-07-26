package com.app.animationapp;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class CustomThread extends Thread {
    public static String TAG = CustomThread.class.getSimpleName();
    private final BaseSurfaceView baseSurfaceView;
    private final SurfaceHolder surfaceHolder;

    private boolean isRunning;
    public CustomThread(BaseSurfaceView baseSurfaceView, SurfaceHolder surfaceHolder) {
        this.baseSurfaceView = baseSurfaceView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        Log.d(TAG,"run");
        while (isRunning) {
            baseSurfaceView.update();
            if (surfaceHolder.getSurface().isValid()) {
                final Canvas canvas = surfaceHolder.lockCanvas();
                baseSurfaceView.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            Log.d(TAG,"sleep");
            sleep(17);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void onStart() {
        Log.d(TAG,"resume");
        isRunning = true;
        start();
    }

    public void onPause() {
        try {
            Log.d(TAG,"pause");
            isRunning = false;
            join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}