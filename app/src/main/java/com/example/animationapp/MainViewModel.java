package com.example.animationapp;

import android.view.View;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final static float CLICK_DRAG_TOLERANCE = 10;

    private float downRawX, downRawY;
    private float dX, dY;

    public MainViewModel() {

    }

    public void setDownRawX(float downRawX) {
        this.downRawX = downRawX;
    }

    public void setDownRawY(float downRawY) {
        this.downRawY = downRawY;
    }

    public void setdX(float dX) {
        this.dX = dX;
    }

    public void setdY(float dY) {
        this.dY = dY;
    }

    public float getDownRawX() {
        return downRawX;
    }

    public float getDownRawY() {
        return downRawY;
    }

    public float getdX() {
        return dX;
    }

    public float getdY() {
        return dY;
    }

    public boolean canClick(float upDX, float upDY) {
        return Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE;
    }

    public boolean canClick(View view, float upDX, float upDY) {
        if (canClick(upDX, upDY))
            return view.performClick();
        else
            return true; // Consumed
    }
}