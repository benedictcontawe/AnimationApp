package com.app.animationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FerrisWheelSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback {

    public static String TAG = FerrisWheelSurfaceView.class.getSimpleName();
    public static FerrisWheelSurfaceView newInstance(Context context, int screenX, int screenY) {
        return new FerrisWheelSurfaceView(context, screenX, screenY);
    }
    private CustomThread thread;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private SnowFlakesParticle snow;
    private List<CircleParticle> circles;
    private List<PointF> initialCirclePositions;
    private float angle = 0;
    private float angleOrbit = 0;

    public FerrisWheelSurfaceView(Context context) {
        super(context);
    }

    public FerrisWheelSurfaceView(Context context , AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FerrisWheelSurfaceView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public FerrisWheelSurfaceView(Context context, int screenX, int screenY) {
        super(context);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY);
    }

    public FerrisWheelSurfaceView(Context context , AttributeSet attributeSet, int screenX, int screenY) {
        super(context, attributeSet);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY);
    }

    public FerrisWheelSurfaceView(Context context, AttributeSet attributeSet, int defStyle, int screenX, int screenY) {
        super(context, attributeSet, defStyle);
        Log.d(TAG,"Constructor");
        initialize(screenX, screenY);
    }

    private void initialize(int screenX, int screenY) {
        setZOrderOnTop(true);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        paint = new Paint();
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        thread = new CustomThread(this, surfaceHolder);
        //region Initialize Wheel
        float centerX = (float) screenX / 2;
        float centerY = (float) screenY / 2;
        snow = new SnowFlakesParticle(getResources(), screenRatioX, screenRatioY)
            .setSpawnCenterX((int) centerX)
            .setSpawnCenterY((int) centerY)
            .build();
        initialCirclePositions = new ArrayList<>();
        circles = new ArrayList<>();
        float radius = (float) screenY / 5;
        initialCirclePositions.add(new PointF(centerX, centerY - radius));
        initialCirclePositions.add(new PointF(centerX + radius * (float) Math.cos(Math.toRadians(45)), centerY - radius * (float) Math.sin(Math.toRadians(45))));
        initialCirclePositions.add(new PointF(centerX + radius, centerY));
        initialCirclePositions.add(new PointF(centerX + radius * (float) Math.cos(Math.toRadians(45)), centerY + radius * (float) Math.sin(Math.toRadians(45))));
        initialCirclePositions.add(new PointF(centerX, centerY + radius));
        initialCirclePositions.add(new PointF(centerX - radius * (float) Math.cos(Math.toRadians(45)), centerY + radius * (float) Math.sin(Math.toRadians(45))));
        initialCirclePositions.add(new PointF(centerX - radius, centerY));
        initialCirclePositions.add(new PointF(centerX - radius * (float) Math.cos(Math.toRadians(45)), centerY - radius * (float) Math.sin(Math.toRadians(45))));
        for (PointF position : initialCirclePositions) {
            CircleParticle circle = new CircleParticle(getResources(), screenRatioX, screenRatioY)
                .setSpawnCenterX((int) position.x)
                .setSpawnCenterY((int) position.y)
                .build();
            circles.add(circle);
        }
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

    }

    @Override
    public void update() {
        Log.d(TAG, "update");
        angle++;
        angleOrbit--;
        float centerX = screenX / 2;
        float centerY = screenY / 2;

        for (int index = 0; index < circles.size(); index++) {
            PointF initialPosition = initialCirclePositions.get(index);
            float dx = initialPosition.x - centerX;
            float dy = initialPosition.y - centerY;

            float newX = centerX + (float) (dx * Math.cos(Math.toRadians(angleOrbit)) - dy * Math.sin(Math.toRadians(angleOrbit)));
            float newY = centerY + (float) (dx * Math.sin(Math.toRadians(angleOrbit)) + dy * Math.cos(Math.toRadians(angleOrbit)));

            CircleParticle circle = circles.get(index);
            circle.setSpawnCenterX((int) newX);
            circle.setSpawnCenterY((int) newY);
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
                final RectF touchBoxCollider2D = new RectF(x - 3, y - 3, x + 3, y + 3);
                for (CircleParticle circle : circles) {
                    circle.onTriggerCollide(touchBoxCollider2D);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"onTouchEvent ACTION_MOVE x " + event.getX() + " y " + event.getY() + " pointer count" + event.getPointerCount());
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