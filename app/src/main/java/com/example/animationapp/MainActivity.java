package com.example.animationapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private AppCompatButton button;
    private Boolean isSlided;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (AppCompatButton) findViewById(R.id.button);
        layout = (ConstraintLayout) findViewById(R.id.layout);
        button.setOnClickListener(this);

        isSlided = false;
    }

    @Override
    public void onClick(View view) {
        if (isSlided){
            animateRight();
        }
        else {
            animateLeft();
        }
    }

    private void animateLeft() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(layout, View.TRANSLATION_X, getSliderPrecentage(false, this, 0.3f));
        objectAnimator.setDuration(250);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.e(MainActivity.class.getSimpleName(),"animateRight() onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e(MainActivity.class.getSimpleName(),"animateRight() onAnimationEnd()");
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.e(MainActivity.class.getSimpleName(),"animateRight() " + valueAnimator.getAnimatedValue().toString());
            }
        });
        objectAnimator.start();
        isSlided = true;
    }

    private void animateRight() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(layout, View.TRANSLATION_X, getSliderPrecentage(true, this, 0.3f));
        objectAnimator.setDuration(250);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.e(MainActivity.class.getSimpleName(),"animateRight() onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e(MainActivity.class.getSimpleName(),"animateRight() onAnimationEnd()");
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.e(MainActivity.class.getSimpleName(),"animateRight() " + valueAnimator.getAnimatedValue().toString());
            }
        });
        objectAnimator.start();
        isSlided = false;
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
