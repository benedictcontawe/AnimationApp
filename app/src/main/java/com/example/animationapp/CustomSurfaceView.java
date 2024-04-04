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

    public static CustomSurfaceView newInstance(Context context, int screenX, int screenY) {
        return new CustomSurfaceView(context, screenX, screenY);
    }
    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private final Paint paint;
    private Random random;
    private Terrain terrainLead, terrainTrail;
    private List<DropParticle> drops;
    private List<DiamondCollectible> diamonds;
    public CustomSurfaceView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        paint = new Paint();
        random = new Random();
        //region Initialize Terrain
        terrainLead = new Terrain(getResources(), screenX, screenY).build();
        terrainTrail = new Terrain(getResources(), screenX, screenY).setSpawnX(screenX).build();
        //endregion
        //region Initialize Drops
        drops = new ArrayList<>();
        for (int index = 0; index < 31; index++) {
            final DropParticle drop = new DropParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnX(getRandomInt(0, screenX))
                .setSpawnY(0)
                .setSpawnDelay(getRandomInt(0, 30))
                .build();
            drops.add(index, drop);
        }
        //endregion
        //region Initialize Diamonds
        diamonds = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            final DiamondCollectible diamond = new DiamondCollectible(getResources(), screenRatioX, screenRatioY)
                .setSpawnX(getRandomInt(0, screenX))
                .setSpawnY(0)
                .setSpawnDelay(getRandomInt(0, 30))
                .build();
            diamonds.add(index, diamond);
        }
        //endregion
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
        //region Update Terrain
        terrainLead.x -= 10 * screenRatioX;
        terrainTrail.x -= 10 * screenRatioX;
        if (terrainLead.x + terrainLead.bitmap.getWidth() < 0) {
            terrainLead.x = screenX;
        }
        if (terrainTrail.x + terrainTrail.bitmap.getWidth() < 0) {
            terrainTrail.x = screenX;
        }
        //endregion
        //region Update Drops
        List<DropParticle> puddles = new ArrayList<>();
        for (DropParticle drop : drops) {
            if (drop.y > screenY) puddles.add(drop);

            if (drop.isSpawnDelayFinished()) {
                drop.y += (int) (5 * screenRatioY);
            } else {
                drop.updateSpawnDelay(1);
            }
        }
        for (DropParticle drop : puddles) {
            drops.remove(drop);
        }
        //endregion
        //region Update Diamonds
        List<DiamondCollectible> stones = new ArrayList<>();
        for (DiamondCollectible diamond : diamonds) {
            if (diamond.y > screenY) stones.add(diamond);

            if (diamond.isSpawnDelayFinished()) {
                diamond.y += (int) (5 * screenRatioY);
            } else {
                diamond.updateSpawnDelay(1);
            }
        }
        for (int index = 0; index < stones.size(); index++) {
            stones.remove(diamonds.get(index));
        }
        //endregion
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            //region Draw Terrain
            canvas.drawBitmap(terrainLead.bitmap, terrainLead.x, terrainLead.y, paint);
            canvas.drawBitmap(terrainTrail.bitmap, terrainTrail.x, terrainTrail.y, paint);
            //endregion
            //region Draw Drops
            for (DropParticle drop : drops) {
                if (drop.isSpawnDelayFinished())
                    canvas.drawBitmap(drop.getBitmap(), drop.x, drop.y, paint);
            }
            //endregion
            //region Update Diamonds
            for (DiamondCollectible diamond : diamonds) {
                if (diamond.isSpawnDelayFinished())
                    canvas.drawBitmap(diamond.getBitmap(), diamond.x, diamond.y, paint);
            }
            //endregion
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