package com.example.animationapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

public class MagneticFloatingActionButton extends MovableFloatingActionButton {

    public MagneticFloatingActionButton(Context context) {
        super(context);
    }

    public MagneticFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MagneticFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setActionDown(view, motionEvent);
                return false; // not Consumed for ripple effect
            case MotionEvent.ACTION_MOVE:
                setActionMove(view, motionEvent, layoutParams);
                return true; // Consumed
            case MotionEvent.ACTION_UP:
                setActionUp(view, motionEvent);

                if (newX > ((parentWidth - viewWidth - layoutParams.rightMargin) / 2)) {
                    newX = parentWidth - viewWidth - layoutParams.rightMargin;
                } else {
                    newX = layoutParams.leftMargin;
                }

                view.animate()
                        .x(newX)
                        .y(newY)
                        .setInterpolator(new OvershootInterpolator())
                        .setDuration(300)
                        .start();

                return canClick(view, upDX, upDY);
            default:
                return super.onTouchEvent(motionEvent);
        }
    }
}
