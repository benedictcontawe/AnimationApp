package com.example.animationapp;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private AppCompatButton button;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (AppCompatButton) findViewById(R.id.button);
        layout = (ConstraintLayout) findViewById(R.id.layout);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    private void animateLeft() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(layout, View.TRANSLATION_X, getSliderPrecentage(false, this, 70f));
        objectAnimator.setDuration(250);
        objectAnimator.start();
    }

    private void animateRight() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(layout, View.TRANSLATION_X, getSliderPrecentage(true, this, 70f));
        objectAnimator.setDuration(250);
        objectAnimator.start();
    }

    private Float getSliderPrecentage(Boolean slideLeft, Activity activity, Float percentage) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        if(slideLeft){
            return 0f;
        } else {
            return (-1 * point.x * percentage);
        }
    }
}
