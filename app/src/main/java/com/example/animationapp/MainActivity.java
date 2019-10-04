package com.example.animationapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private SeekBar seekBar;
    private int selectedThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        button =(Button) findViewById(R.id.button);

        //seekBar.setPadding(0,0,0,0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.e("MainActivity ","Seek Bar Tag " + seekBar.getTag().toString());
        //Log.e("MainActivity ","Seek Bar Thumb " + seekBar.getThumb().toString());
        if (seekBar.getTag() == null){
            Log.e(MainActivity.class.getSimpleName(),"seeker tag null");
        }
        else {
            Log.e(MainActivity.class.getSimpleName(),"seeker tag not null");
        }

        selectedThumb = -7;

        button.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        button.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        new CountDownTimer(1000, 40){
            public void onTick(long millisUntilFinished){

                switch (selectedThumb){
                    case 1:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_1st));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_1st);
                        selectedThumb = 2;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_1st");
                        break;
                    case 2:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_2nd));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_2nd);
                        selectedThumb = 3;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_2nd");
                        break;
                    case 3:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_3rd));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_3rd);
                        selectedThumb = 4;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_3rd");
                        break;
                    case 4:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_4th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_4th);
                        selectedThumb = 5;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_4th");
                        break;
                    case 5:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_5th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_5th);
                        selectedThumb = 6;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_5th");
                        break;
                    case 6:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_6th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_6th);
                        selectedThumb = 7;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_6th");
                        break;
                    case 7:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_7th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_7th);
                        selectedThumb = 8;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_7th");
                        break;
                    case 8:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_7th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_7th);
                        selectedThumb = 9;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_7th");
                        break;
                    case 9:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_7th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_7th);
                        selectedThumb = 10;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_7th");
                        break;
                    case 10:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_6th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_6th);
                        selectedThumb = 11;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_6th");
                        break;
                    case 11:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_5th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_5th);
                        selectedThumb = 11;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_5th");
                        break;
                    default:
                        seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_0th));
                        seekBar.setTag(R.drawable.ic_seeker_thumb_selected_0th);
                        selectedThumb++;
                        Log.e(MainActivity.class.getSimpleName(),"ic_seeker_thumb_selected_0th");
                        break;
                }
            }
            public  void onFinish(){
                //seekBar.setThumb(ContextCompat.getDrawable(this,R.drawable.ic_seeker_thumb_selected_5th))
                seekBar.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected_5th));
                seekBar.setTag(R.drawable.ic_seeker_thumb_selected_5th);
                selectedThumb = -7;
                Log.e(MainActivity.class.getSimpleName(),"onFinish()");
                if (seekBar.getTag().equals(R.drawable.ic_seeker_thumb_selected_5th)){
                    Log.e(MainActivity.class.getSimpleName(),"seeker thumb equal");
                }
                else{
                    Log.e(MainActivity.class.getSimpleName(),"seeker thumb not equal");
                }
            }
        }.start();
    }
}
