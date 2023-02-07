package com.example.animationapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    public static void LogDebug(String TAG, String message) {
        Log.d(TAG,message);
    }

    private final static float CLICK_DRAG_TOLERANCE = 10;
    private float downRawX, downRawY, dX, dY, newX, newY;
    private int viewWidth, viewHeight, parentWidth, parentHeight;

    public MainViewModel() {

    }

    public void setDownRawX(float downRawX) {
        this.downRawX = downRawX;
    }

    public void setDownRawY(float downRawY) {
        this.downRawY = downRawY;
    }

    public void setDx(float dX) {
        this.dX = dX;
    }

    public void setDy(float dY) {
        this.dY = dY;
    }

    public void setNewX(ViewGroup.MarginLayoutParams layoutParams) {
        if (newX > ((parentWidth - viewWidth - layoutParams.rightMargin) / 2)) {
            newX = parentWidth - viewWidth - layoutParams.rightMargin;
        } else {
            newX = layoutParams.leftMargin;
        }
    }

    public void setNewX(float rawX, ViewGroup.MarginLayoutParams layoutParams) {
        this.newX = rawX + dX;
        newX = Math.max(layoutParams.leftMargin, newX); // Don't allow the FAB past the left hand side of the parent
        newX = Math.min(parentWidth - viewWidth - layoutParams.rightMargin, newX); // Don't allow the FAB past the right hand side of the parent
    }

    public void setNewY(float rawY, ViewGroup.MarginLayoutParams layoutParams) {
        newY = rawY + dY;
        newY = Math.max(layoutParams.topMargin, newY); // Don't allow the FAB past the top of the parent
        newY = Math.min(parentHeight - viewHeight - layoutParams.bottomMargin, newY); // Don't allow the FAB past the bottom of the parent
    }


    public void setViewDimension(View view) {
        this.viewWidth = view.getWidth();
        this.viewHeight = view.getHeight();
    }

    public void setParentDimension(ViewParent viewParent) {
        this.parentWidth = ( (View) viewParent ).getWidth();
        this.parentHeight = ( (View) viewParent ).getHeight();
    }

    public float getDownRawX() {
        return downRawX;
    }

    public float getDownRawY() {
        return downRawY;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }

    private void setRippleEffect(View view, long delayMillis) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setPressed(true);
            }
        },delayMillis);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setPressed(false);
            }
        },delayMillis);
    }

    private boolean canClick(float upDX, float upDY) {
        return Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE;
    }

    public boolean canClick(View view, float upDX, float upDY) {
        if (canClick(upDX, upDY)) {
            setRippleEffect(view, 250);
            return view.performClick();
        } else {
            setRippleEffect(view, 250);
            return true; //Consumed
        }
    }
}