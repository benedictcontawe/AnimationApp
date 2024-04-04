package com.example.animationapp;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class CustomThread extends Thread {
    public static String TAG = CustomThread.class.getSimpleName();
    private final CustomSurfaceView customSurfaceView;
    private final SurfaceHolder surfaceHolder;

    private boolean isRunning;
    public CustomThread(CustomSurfaceView customSurfaceView, SurfaceHolder surfaceHolder) {
        this.customSurfaceView = customSurfaceView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        Log.d(TAG,"run");
        while (isRunning) {
            customSurfaceView.update();
            if (surfaceHolder.getSurface().isValid()) {
                final Canvas canvas = surfaceHolder.lockCanvas();
                customSurfaceView.draw(canvas);
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
