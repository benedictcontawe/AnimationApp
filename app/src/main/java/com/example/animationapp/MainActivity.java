package com.example.animationapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout normalContainer;
    private Button button;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normalContainer = (ConstraintLayout) findViewById(R.id.normalContainer);
        button = (Button) findViewById(R.id.button);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        linearLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setPressed(true);
            }
        },250);
        linearLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setPressed(false);
            }
        },250);

        /*normalContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                normalContainer.setPressed(true);
            }
        },250);
        normalContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                normalContainer.setPressed(false);
            }
        },250);*/
    }
}
