package com.example.animationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        installSplashScreen().setKeepOnScreenCondition{ false }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getWindow().setBackgroundDrawableResource(R.drawable.bitmap_splash)
    }
}