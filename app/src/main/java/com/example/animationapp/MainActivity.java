package com.example.animationapp;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AnimationDrawable animationDrawable;
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        button =(Button) findViewById(R.id.button);
    }

    @Override
    protected void onStart() {
        super.onStart();

        imageView.setBackgroundResource(R.drawable.custom_animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        button.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        button.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        animationDrawable.stop();
        //animationDrawable.selectDrawable(0);
        animationDrawable.start();
    }
}
