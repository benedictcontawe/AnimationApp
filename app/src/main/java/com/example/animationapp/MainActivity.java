package com.example.animationapp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ImageView imageView;
    private AppCompatButton button;
    private float currentDegrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        button = (AppCompatButton) findViewById(R.id.button);
        button.setOnClickListener(this);

        currentDegrees = 0;
    }

    private void turnArrowDown(){
        //RotateAnimation anim = new RotateAnimation(currentRotation, currentRotation + 30, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        //long currentRotation; = (currentRotation + 30) % 360;

        RotateAnimation anim = new RotateAnimation(currentDegrees, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(500);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(),"onAnimationEnd turnArrowDown()",Toast.LENGTH_SHORT).show();
                currentDegrees = 180;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        imageView.startAnimation(anim);
    }

    private void turnArrowUp(){
        RotateAnimation anim = new RotateAnimation(currentDegrees, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(500);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(),"onAnimationEnd turnArrowUp()",Toast.LENGTH_SHORT).show();
                currentDegrees = 0;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        imageView.startAnimation(anim);
    }

    @Override
    public void onClick(View view) {
        if (currentDegrees == 0){
            turnArrowDown();
        }
        else if(currentDegrees == 180){
            turnArrowUp();
        }
        else if(currentDegrees == -180){
            turnArrowDown();
        }
    }
}
