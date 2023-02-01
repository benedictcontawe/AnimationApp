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

    private MainBinder binder;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binder.setViewModel(viewModel);
        binder.setLifecycleOwner(this);
        binder.movableFloatingActionButtonAndroid.setOnClickListener(this::onClick);
        binder.floatingActionButtonAndroid.setOnClickListener(this::onClick);
        binder.floatingActionButtonAndroid.setOnTouchListener(this::onTouch);
    }

    @Override
    public void onClick(View view) {
        if (view == binder.movableFloatingActionButtonAndroid)
            Toast.makeText(this, "Movable Floating Action Button", Toast.LENGTH_SHORT).show();
        else if (view == binder.floatingActionButtonAndroid)
            Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
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
                int viewWidth = view.getWidth();
                int viewHeight = view.getHeight();

                View viewParent = (View)view.getParent();
                int parentWidth = viewParent.getWidth();
                int parentHeight = viewParent.getHeight();

                float newX = motionEvent.getRawX() + binder.getViewModel().getdX();
                newX = Math.max(layoutParams.leftMargin, newX); // Don't allow the FAB past the left hand side of the parent
                newX = Math.min(parentWidth - viewWidth - layoutParams.rightMargin, newX); // Don't allow the FAB past the right hand side of the parent

                float newY = motionEvent.getRawY() + binder.getViewModel().getdY();
                newY = Math.max(layoutParams.topMargin, newY); // Don't allow the FAB past the top of the parent
                newY = Math.min(parentHeight - viewHeight - layoutParams.bottomMargin, newY); // Don't allow the FAB past the bottom of the parent

                view.animate()
                        .x(newX)
                        .y(newY)
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