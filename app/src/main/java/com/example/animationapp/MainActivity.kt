package com.example.animationapp

import android.graphics.Point
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Display
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

public class MainActivity : AppCompatActivity(), View.OnTouchListener{

    private lateinit var display : Display
    private var dX : Float = 0.0f
    private var dY : Float = 0.0f
    private lateinit var size : Point


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewTouch.setOnTouchListener(this)

        size = Point()
        display = windowManager.defaultDisplay
        display.getSize(size)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when(motionEvent.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                dX = view.getX() - motionEvent.getRawX()
                dY = view.getY() - motionEvent.getRawY()
            }
            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x(motionEvent.rawX + dX)
                    .y(motionEvent.rawY + dY)
                    .setDuration(0)
                    .start()
            }
            MotionEvent.ACTION_UP -> {
                view.animate()
                    .x(constraint_guideline_marginStart.getLeft().toFloat())
                    .y(constraint_guideline_marginTop.getTop().toFloat())
                    .setDuration(250)
                    .start()
            }
            else -> {
                return false
            }
        }
        return true
    }
}