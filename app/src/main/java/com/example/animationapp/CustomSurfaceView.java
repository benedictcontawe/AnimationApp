package com.example.animationapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class CustomSurfaceView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private BackgroundTileMap backgroundTileMap;
    public CustomSurfaceView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        backgroundTileMap = new BackgroundTileMap(screenX, screenY, getResources());
        backgroundTileMap.x = screenX;
        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        backgroundTileMap.x -= 10 * screenRatioX;
        if (backgroundTileMap.x + backgroundTileMap.bitmap.getWidth() < 0) {
            backgroundTileMap.x = screenX;
        }
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backgroundTileMap.bitmap, backgroundTileMap.x, backgroundTileMap.y, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            thread.sleep(17);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
