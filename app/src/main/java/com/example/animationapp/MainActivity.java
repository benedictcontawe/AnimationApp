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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout normalContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normalContainer = (ConstraintLayout) findViewById(R.id.normalContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();


        normalContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                normalContainer.setPressed(true);
            }
        },1000);
        normalContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                normalContainer.setPressed(false);
                Toast.makeText(getApplication(),"pressed",Toast.LENGTH_SHORT).show();
            }
        },1000);
    }
}
