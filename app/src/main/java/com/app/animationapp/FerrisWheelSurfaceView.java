package com.app.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FerrisWheelSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback {

    public static String TAG = FerrisWheelSurfaceView.class.getSimpleName();
    public static FerrisWheelSurfaceView newInstance(Context context, int screenX, int screenY, Listener listener) {
        return new FerrisWheelSurfaceView(context, screenX, screenY, listener);
    }
    private CustomThread thread;
    private Listener listener;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private Random random;
    private SnowFlakesParticle snow;
    private List<CircleParticle> circles;
    private float angle = 0;

    public FerrisWheelSurfaceView(Context context) {
        super(context);;
    }

    public FerrisWheelSurfaceView(Context context , AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FerrisWheelSurfaceView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public FerrisWheelSurfaceView(Context context, int screenX, int screenY, Listener listener) {
        super(context);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY, listener);
    }

    public FerrisWheelSurfaceView(Context context , AttributeSet attributeSet, int screenX, int screenY, Listener listener) {
        super(context, attributeSet);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY, listener);
    }

    public FerrisWheelSurfaceView(Context context, AttributeSet attributeSet, int defStyle, int screenX, int screenY, Listener listener) {
        super(context, attributeSet, defStyle);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY, listener);
    }

    private void initialize(int screenX, int screenY, Listener listener) {
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
        //region Initialize Wheel
        snow = new SnowFlakesParticle(getResources(), screenRatioX, screenRatioY)
            .setSpawnCenterX(screenX / 2)
            .setSpawnCenterY(screenY / 2)
            .build();
        circles = new ArrayList<CircleParticle>();
        final CircleParticle north = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX(screenX / 2)
                .setSpawnCenterY((screenY / 2) - (screenY / 13))
                .build();
        circles.add(north);
        final CircleParticle northEast = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((screenX / 2) + (screenX / 5))
                .setSpawnCenterY((screenY / 2) - (screenY / 15))
                .build();
        circles.add(northEast);
        final CircleParticle east = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((screenX / 2) + (screenX / 3))
                .setSpawnCenterY(screenY / 2)
                .build();
        circles.add(east);
        final CircleParticle southEast = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((screenX / 2) + (screenX / 5))
                .setSpawnCenterY((screenY / 2) + (screenY / 13))
                .build();
        circles.add(southEast);
        final CircleParticle south = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX(screenX / 2)
                .setSpawnCenterY((screenY / 2) + (screenY / 13))
                .build();
        circles.add(south);
        final CircleParticle southWest = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((screenX / 2) - (screenX / 5))
                .setSpawnCenterY((screenY / 2) + (screenY / 15))
                .build();
        circles.add(southWest);
        final CircleParticle west = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((screenX / 2) - (screenX / 3))
                .setSpawnCenterY(screenY / 2)
                .build();
        circles.add(west);
        final CircleParticle northWest = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((screenX / 2) - (screenX / 5))
                .setSpawnCenterY((screenY / 2) - (screenY / 15))
                .build();
        circles.add(northWest);
        //endregion
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (thread.getState().equals(Thread.State.TERMINATED)) {
            holder.addCallback(this);
            holder.setFormat(PixelFormat.TRANSPARENT);
            thread = new CustomThread(this, holder);
        }
        thread.onStart();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        //snow.positionX = height / 2;
        //snow.positionY = height / 2;
    }

    @Override
    public void update() {
        Log.d(TAG, "update");
        angle++;
        for (CircleParticle circle : circles) {
            circle.updateBitMap(getResources());
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG,"draw");
        canvas.drawColor(Color.BLACK); //canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        int saveCount = canvas.save();
        canvas.rotate(angle, snow.positionX + (snow.getBitmap().getWidth() / 2), snow.positionY + (snow.getBitmap().getHeight() / 2)); //canvas.rotate(angle, screenX/2, screenY/2);
        canvas.drawBitmap(snow.getBitmap(), snow.positionX, snow.positionY, paint);
        canvas.restoreToCount(saveCount);
        for (CircleParticle circle : circles) {
            canvas.drawBitmap(circle.getBitmap(), circle.positionX, circle.positionY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"onTouchEvent ACTION_DOWN x " + event.getRawX() + " y " + event.getRawY() + " pointer count" + event.getPointerCount());
                int x = Math.round(event.getRawX());
                int y = Math.round(event.getRawY());
                final Rect touchBoxCollider2D = new Rect(x - 3, y - 3, x + 3, y + 3);
                for (CircleParticle circle : circles) {
                    circle.onTriggerCollide(touchBoxCollider2D);
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