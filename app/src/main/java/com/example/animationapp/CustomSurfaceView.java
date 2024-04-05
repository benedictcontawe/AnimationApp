package com.example.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public static String TAG = CustomSurfaceView.class.getSimpleName();
    public static CustomSurfaceView newInstance(Context context, int screenX, int screenY, Listener listener) {
        return new CustomSurfaceView(context, screenX, screenY, listener);
    }
    private CustomThread thread;
    private Listener listener;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private final Paint paint;
    private Random random;
    private Terrain terrainLead, terrainTrail;
    private List<DropParticle> drops;
    private List<DiamondCollectible> diamonds;
    public CustomSurfaceView(Context context, int screenX, int screenY, Listener listener) {
        super(context);
        Log.d(TAG,"Constructor");
        setZOrderOnTop(true);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        this.listener = listener;
        paint = new Paint();
        random = new Random();
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        thread = new CustomThread(this, surfaceHolder);
        //region Initialize Terrain
        terrainLead = new Terrain(getResources(), screenX, screenY).build();
        terrainTrail = new Terrain(getResources(), screenX, screenY).setSpawnX(screenX).build();
        //endregion
        //region Initialize Drops
        drops = new ArrayList<>();
        for (int index = 0; index < 11; index++) {
            final DropParticle drop = new DropParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnX(getRandomInt(0, screenX))
                .setSpawnY(-13)
                .setSpawnDelay(getRandomInt(0, 30))
                .build();
            drops.add(index, drop);
        }
        //endregion
        //region Initialize Diamonds
        diamonds = new ArrayList<>();
        for (int index = 0; index < 13; index++) {
            final DiamondCollectible diamond = new DiamondCollectible(getResources(), screenRatioX, screenRatioY)
                .setSpawnX(getRandomInt(0, screenX))
                .setSpawnY(-13)
                .setSpawnDelay(getRandomInt(0, 30))
                .build();
            diamonds.add(index, diamond);
        }
        //endregion
        setFocusable(true);
    }

    private int getRandomInt(int minimum, int maximum) {
        return random.nextInt((maximum - minimum) + 1) + minimum;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
        if (thread.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = holder;
            surfaceHolder.addCallback(this);
            surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
            thread = new CustomThread(this, surfaceHolder);
        }
        thread.onStart();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    public void update() {
        Log.d(TAG,"update");
        //region Update Terrain
        terrainLead.positionX -= 10 * screenRatioX;
        terrainTrail.positionX -= 10 * screenRatioX;
        if (terrainLead.positionX + terrainLead.bitmap.getWidth() < 0) {
            terrainLead.positionX = screenX;
        }
        if (terrainTrail.positionX + terrainTrail.bitmap.getWidth() < 0) {
            terrainTrail.positionX = screenX;
        }
        //endregion
        //region Update Drops
        List<DropParticle> puddles = new ArrayList<>();
        for (DropParticle drop : drops) {
            if (drop.positionY > screenY) puddles.add(drop);

            if (drop.isSpawnDelayFinished()) {
                drop.positionY += (int) (5 * screenRatioY);
            } else {
                drop.updateSpawnDelay(1);
            }
        }
        for (DropParticle puddle : puddles) {
            drops.remove(puddle);
        }
        //endregion
        //region Update Diamonds
        List<DiamondCollectible> stones = new ArrayList<>();
        for (DiamondCollectible diamond : diamonds) {
            if (diamond.positionY > screenY || diamond.isCollected) stones.add(diamond);
            if (diamond.isSpawnDelayFinished()) {
                diamond.positionY += (int) (5 * screenRatioY);
            } else {
                diamond.updateSpawnDelay(1);
            }
        }
        for (int index = 0; index < stones.size(); index++) {
            diamonds.remove(stones.get(index));
        }
        //endregion
        if (drops.isEmpty() && diamonds.isEmpty()) {
            Log.d(TAG,"Drops and Diamonds are Empty!");
            listener.onCloseGame();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG,"draw");
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        //region Draw Terrain
        canvas.drawBitmap(terrainLead.bitmap, terrainLead.positionX, terrainLead.positionY, paint);
        canvas.drawBitmap(terrainTrail.bitmap, terrainTrail.positionX, terrainTrail.positionY, paint);
        //endregion
        //region Draw Drops
        for (DropParticle drop : drops) {
            if (drop.isSpawnDelayFinished())
                canvas.drawBitmap(drop.getBitmap(), drop.positionX, drop.positionY, paint);
        }
        //endregion
        //region Update Diamonds
        for (DiamondCollectible diamond : diamonds) {
            if (diamond.isSpawnDelayFinished())
                canvas.drawBitmap(diamond.getBitmap(), diamond.positionX, diamond.positionY, paint);
        }
        //endregion
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"onTouchEvent ACTION_DOWN x " + event.getRawX() + " y " + event.getRawY() + " pointer count" + event.getPointerCount());
                int x = Math.round(event.getRawX());
                int y = Math.round(event.getRawY());
                final Rect touchBoxCollider2D = new Rect(x - 3, y - 3, x + 3, y + 3);
                for (DiamondCollectible diamond : diamonds) {
                    diamond.onTriggerCollide(touchBoxCollider2D);
                }
                //if (event.getX() < screenX / 2) Log.d(TAG,"onTouchEvent ACTION_DOWN Left Screen");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"onTouchEvent ACTION_MOVE x " + event.getX() + " y " + event.getY() + " pointer count" + event.getPointerCount());
                //if (event.getX() < screenX / 2) Log.d(TAG,"onTouchEvent ACTION_MOVE Left Screen");
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void onResume() {
        thread.onStart();
    }

    public void onPause() {
        thread.onPause();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
        thread.onPause();
    }
}