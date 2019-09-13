package com.example.animationapp;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

public class MainActivity extends AppCompatActivity implements OnTouchListener {

    private Display display;
    private float dX, dY;
    private Point size;
    private View view;
    private Guideline constraint_guideline_marginTop, constraint_guideline_marginStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (View) findViewById(R.id.view);
        constraint_guideline_marginTop = (Guideline) findViewById(R.id.constraint_guideline_marginTop);
        constraint_guideline_marginStart = (Guideline) findViewById(R.id.constraint_guideline_marginStart);
        view.setOnTouchListener(this);

        size = new Point();
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == view.getId()) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - motionEvent.getRawX();
                    dY = view.getY() - motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    view.animate()
                            .x(motionEvent.getRawX() + dX)
                            .y(motionEvent.getRawY() + dY)
                            .setDuration(0)
                            .start();
                    break;
                case MotionEvent.ACTION_UP:
                    view.animate()
                            .x(constraint_guideline_marginStart.getLeft())
                            .y(constraint_guideline_marginTop.getTop())
                            .setDuration(250)
                            .start();
                default:
                    return false;
            }
            return true;
        }
        else {
            return false;
        }
    }
}
