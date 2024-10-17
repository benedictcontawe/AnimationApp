package com.app.animationapp;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView {

    protected CustomThread thread;
    protected final Paint paint;
    protected int screenX, screenY;
    protected float screenRatioX, screenRatioY;

    public BaseSurfaceView(Context context) {
        super(context);
        paint = new Paint();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
    }

    abstract public void update();
}