package com.example.animationapp;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AnimationDrawable animationDrawable;
    private Button button;
    private ImageView imageView;
    private SeekBar seekBar;
    private int selectedThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        imageView.setBackgroundResource(R.drawable.custom_animation);

        animationDrawable = (AnimationDrawable) imageView.getBackground();

        button =(Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        animationDrawable.stop();
        //animationDrawable.selectDrawable(0);
        animationDrawable.start();

        new CountDownTimer(1000, 40){
            public void onTick(long millisUntilFinished){

                switch (selectedThumb){
                    case 0:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_0th));
                        selectedThumb = 1;
                        break;
                    case 1:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_1st));
                        selectedThumb = 2;
                        break;
                    case 2:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_2nd));
                        selectedThumb = 3;
                        break;
                    case 3:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_3rd));
                        selectedThumb = 4;
                        break;
                    case 4:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_4th));
                        selectedThumb = 5;
                        break;
                    case 5:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_5th));
                        selectedThumb = 6;
                        break;
                    case 6:
                        break;
                    default:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_0th));
                        selectedThumb++;
                        break;
                }
            }
            public  void onFinish(){
                seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_5th));
                selectedThumb = -5;
            }
        }.start();
    }
}
