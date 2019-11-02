package com.example.animationapp

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var animationDrawable : AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        imageView.setBackgroundResource(R.drawable.custom_animation)
        animationDrawable = imageView!!.background as AnimationDrawable

        button.setOnClickListener(this)
    }

    override fun onStop() {
        super.onStop()
        button.setOnClickListener(null)
    }

    override fun onClick(view: View) {
        //animationDrawable.selectDrawable(0);
        if (animationDrawable.isRunning) {
            animationDrawable.stop()
            animationDrawable.start()
        } else {
            animationDrawable.start()
        }
    }
}