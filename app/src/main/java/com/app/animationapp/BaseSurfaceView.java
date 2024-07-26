package com.app.animationapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView {

    public BaseSurfaceView(Context context) {
        super(context);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    abstract public void update();
}