package com.example.animationapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isSlided : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)

        isSlided = false
    }

    override fun onClick(view: View) {
        if (isSlided!!) {
            animateLeft()
        } else {
            animateRight()
        }
    }

    private fun animateRight() {
        val objectAnimator : ObjectAnimator = ObjectAnimator.ofFloat(constraintLayout, View.TRANSLATION_X, getSliderPrecentage(false, this, 0.3f))
        objectAnimator.duration = 500
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                Log.e(MainActivity::class.java.simpleName, "animateRight() onAnimationStart()")
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                Log.e(MainActivity::class.java.simpleName, "animateRight() onAnimationEnd()")
            }
        })
        objectAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator -> Log.e(MainActivity::class.java.simpleName, "animateRight() " + valueAnimator.animatedValue.toString()) })
        objectAnimator.start()
        isSlided = true
    }

    private fun animateLeft() {
        val objectAnimator = ObjectAnimator.ofFloat(constraintLayout, View.TRANSLATION_X, getSliderPrecentage(true, this, 0.3f))
        objectAnimator.setDuration(500)
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                Log.e(MainActivity::class.java.simpleName, "animateRight() onAnimationStart()")
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                Log.e(MainActivity::class.java.simpleName, "animateRight() onAnimationEnd()")
            }
        })
        objectAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator -> Log.e(MainActivity::class.java.simpleName, "animateRight() " + valueAnimator.animatedValue.toString()) })
        objectAnimator.start()
        isSlided = false
    }

    private fun getSliderPrecentage(slideLeft : Boolean, activity: Activity, percentage : Float) : Float {
        val display : Display = activity.windowManager.defaultDisplay
        val point : Point = Point()
        display.getSize(point)
        return if(slideLeft){
            0f
        } else {
            (-1 * point.x * percentage)
        }

    }
}