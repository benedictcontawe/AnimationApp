package com.example.animationapp;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main); //setContentView(CustomSurfaceView(this, getWidth(), getHeight()));
        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, RainFragment.newInstance())
            .addToBackStack(RainFragment.TAG).commit();
    }
}