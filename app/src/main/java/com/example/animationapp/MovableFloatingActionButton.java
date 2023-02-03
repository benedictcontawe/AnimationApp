package com.example.animationapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovableFloatingActionButton extends FloatingActionButton implements View.OnTouchListener {

    private static final String TAG = MovableFloatingActionButton.class.getSimpleName();
    protected final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
    protected float downRawX, downRawY, dX, dY, newX, newY, upRawX, upRawY, upDX, upDY;
    protected int viewWidth, viewHeight, parentWidth, parentHeight;

    public MovableFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public MovableFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovableFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setOnTouchListener(this);
    }

    protected boolean canClick(View view, float upDX, float upDY) {
        if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE)
            return view.performClick();
        else
            return true; // Consumed
    }

    protected void setActionDown(View view, MotionEvent motionEvent) {
        downRawX = motionEvent.getRawX();
        downRawY = motionEvent.getRawY();
        dX = view.getX() - downRawX;
        dY = view.getY() - downRawY;
    }

    protected void setActionMove(View view, MotionEvent motionEvent, ViewGroup.MarginLayoutParams layoutParams) {
        viewWidth = view.getWidth();
        viewHeight = view.getHeight();

        View viewParent = (View)view.getParent();
        parentWidth = viewParent.getWidth();
        parentHeight = viewParent.getHeight();

        newX = motionEvent.getRawX() + dX;
        newX = Math.max(layoutParams.leftMargin, newX); // Don't allow the FAB past the left hand side of the parent
        newX = Math.min(parentWidth - viewWidth - layoutParams.rightMargin, newX); // Don't allow the FAB past the right hand side of the parent

        newY = motionEvent.getRawY() + dY;
        newY = Math.max(layoutParams.topMargin, newY); // Don't allow the FAB past the top of the parent
        newY = Math.min(parentHeight - viewHeight - layoutParams.bottomMargin, newY); // Don't allow the FAB past the bottom of the parent

        view.animate()
                .x(newX)
                .y(newY)
                .setDuration(0)
                .start();
    }

    protected void setActionUp(View view, MotionEvent motionEvent) {
        upRawX = motionEvent.getRawX();
        upRawY = motionEvent.getRawY();

        upDX = upRawX - downRawX;
        upDY = upRawY - downRawY;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setActionDown(view, motionEvent);
                return true; // Consumed
            case MotionEvent.ACTION_MOVE:
                setActionMove(view, motionEvent, layoutParams);
                return true; // Consumed
            case MotionEvent.ACTION_UP:
                setActionUp(view, motionEvent);
                return canClick(view, upDX, upDY);
            default:
                return super.onTouchEvent(motionEvent);
        }
    }
}