package com.example.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomSurfaceView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private final Paint paint;
    private Random random;
    private Terrain terrainLead, terrainTrail;
    private List<DropParticle> drops;
    public CustomSurfaceView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        paint = new Paint();
        random = new Random();
        terrainLead = new Terrain(getResources(), screenX, screenY);
        terrainTrail = new Terrain(getResources(), screenX, screenY);
        terrainTrail.x = screenX;
        drops = new ArrayList<>();
        for (int index = 0; index < 30; index++) {
            final DropParticle drop = new DropParticle(getResources(), screenRatioX, screenRatioY)
                    .setSpawnX(getRandomInt(0, screenX))
                    .setSpawnY(0)
                    .setSpawnDelay(getRandomInt(0, 30))
                    .build();
            drops.add(index, drop);
        }
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
        terrainLead.x -= 10 * screenRatioX;
        terrainTrail.x -= 10 * screenRatioX;
        if (terrainLead.x + terrainLead.bitmap.getWidth() < 0) {
            terrainLead.x = screenX;
        }
        if (terrainTrail.x + terrainTrail.bitmap.getWidth() < 0) {
            terrainTrail.x = screenX;
        }
        List<DropParticle> trash = new ArrayList<>();
        for (DropParticle drop : drops) {
            if (drop.y > screenY) trash.add(drop);

            if (drop.isSpawnDelayFinished()) {
                drop.y += (int) (5 * screenRatioY);
            } else {
                drop.updateSpawnDelay(1);
            }
        }
        for (DropParticle drop : trash) {
            drops.remove(drop);
        }
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(terrainLead.bitmap, terrainLead.x, terrainLead.y, paint);
            canvas.drawBitmap(terrainTrail.bitmap, terrainTrail.x, terrainTrail.y, paint);
            for (DropParticle drop : drops) {
                if (drop.isSpawnDelayFinished())
                    canvas.drawBitmap(drop.getBitmap(), drop.x, drop.y, paint);
            }
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private int getRandomInt(int minimum, int maximum) {
        return random.nextInt((maximum - minimum) + 1) + minimum;
    }
}