package com.example.animationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.DataBindingUtil
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.animationapp.databinding.MainBinder

class MainActivity : AppCompatActivity(), OnClickListener {

    private var binder : MainBinder? = null
    private var isPlay : Boolean? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binder?.setLifecycleOwner(this@MainActivity)
        super.onCreate(savedInstanceState)
        initializeAnimation()
    }

    private fun initializeAnimation() {
        binder?.floatingActionButton?.setImageDrawable(
            getAnimatedVectorDrawable()
        )
        binder?.floatingActionButton?.setOnClickListener(this@MainActivity)
    }

    override fun onClick(view : View?) {
        if (view == binder?.floatingActionButton) {
            toggleBoolean()
            binder?.floatingActionButton?.setImageDrawable(
                getAnimatedVectorDrawable()
            )
            val animatable : Animatable2Compat = binder?.floatingActionButton?.getDrawable() as Animatable2Compat
            animatable.start()
        }
    }

    private fun toggleBoolean() {
        if (isPlay == true)
            isPlay = false
        else
            isPlay = true
    }

    private fun getAnimatedVectorDrawable() : AnimatedVectorDrawableCompat? {
        val play : AnimatedVectorDrawableCompat? = AnimatedVectorDrawableCompat.create(this@MainActivity, R.drawable.anim_play_to_pause)
        val pause : AnimatedVectorDrawableCompat? = AnimatedVectorDrawableCompat.create(this@MainActivity, R.drawable.anim_pause_to_play)
        return if (isPlay == true) pause else play
    }
}