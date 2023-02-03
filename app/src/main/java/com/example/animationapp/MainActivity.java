package com.example.animationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.animationapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainBinder binder;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binder.setViewModel(viewModel);
        binder.setLifecycleOwner(this);
        binder.floatingActionButtonAndroid.setOnClickListener(this::onClick);
        binder.movableFloatingActionButtonAndroid.setOnClickListener(this::onClick);
        binder.floatingActionButtonAndroid.setOnTouchListener(this::onTouch);
    }

    @Override
    public void onClick(View view) {
        if (view == binder.floatingActionButtonAndroid)
            Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
        else if (view == binder.movableFloatingActionButtonAndroid)
            Toast.makeText(this, "Movable Floating Action Button", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                binder.getViewModel().setDownRawX(motionEvent.getRawX());
                binder.getViewModel().setDownRawY(motionEvent.getRawY());
                binder.getViewModel().setdX(view.getX() - binder.getViewModel().getDownRawX());
                binder.getViewModel().setdY(view.getY() - binder.getViewModel().getDownRawY());
                return true; // Consumed
            case MotionEvent.ACTION_MOVE:
                binder.getViewModel().setViewDimension(view);
                binder.getViewModel().setParentDimension(view.getParent());
                binder.getViewModel().setNewX(motionEvent.getRawX(), layoutParams);
                binder.getViewModel().setNewY(motionEvent.getRawY(), layoutParams);
                view.animate()
                        .x( binder.getViewModel().getNewX() )
                        .y( binder.getViewModel().getNewY() )
                        .setDuration(0)
                        .start();
                return true; // Consumed
            case MotionEvent.ACTION_UP:
                float upRawX = motionEvent.getRawX();
                float upRawY = motionEvent.getRawY();

                float upDX = upRawX - binder.getViewModel().getDownRawX();
                float upDY = upRawY - binder.getViewModel().getDownRawY();

                return binder.getViewModel().canClick(view, upDX, upDY);
            default:
                return super.onTouchEvent(motionEvent);
        }
    }
}