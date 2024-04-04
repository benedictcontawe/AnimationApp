package com.example.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
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
    public static CustomSurfaceView newInstance(Context context, int screenX, int screenY) {
        return new CustomSurfaceView(context, screenX, screenY);
    }
    private CustomThread runnable;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private final Paint paint;
    private Random random;
    private Terrain terrainLead, terrainTrail;
    private List<DropParticle> drops;
    private List<DiamondCollectible> diamonds;
    public CustomSurfaceView(Context context, int screenX, int screenY) {
        super(context);
        Log.d(TAG,"Constructor");
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        paint = new Paint();
        random = new Random();
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        runnable = new CustomThread(this, surfaceHolder);
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
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
        runnable.onStart();
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
        for (DropParticle drop : puddles) {
            drops.remove(drop);
        }
        //endregion
        //region Update Diamonds
        List<DiamondCollectible> stones = new ArrayList<>();
        for (DiamondCollectible diamond : diamonds) {
            if (diamond.positionY > screenY) stones.add(diamond);

            if (diamond.isSpawnDelayFinished()) {
                diamond.positionY += (int) (5 * screenRatioY);
            } else {
                diamond.updateSpawnDelay(1);
            }
        }
        for (int index = 0; index < stones.size(); index++) {
            stones.remove(diamonds.get(index));
        }
        //endregion
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG,"draw with canvas");
        if(getHolder().getSurface().isValid()) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
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
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    Log.d(TAG,"Touching Left Screen");
                }
                return true;
            case MotionEvent.ACTION_MOVE:
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

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
        runnable.onPause();
    }
}