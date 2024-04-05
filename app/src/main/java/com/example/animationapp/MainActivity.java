package com.example.animationapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Listener {

    public static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main); //setContentView(CustomSurfaceView(this, getWidth(), getHeight()));
        final ImageView imageView = findViewById(R.id.image_view);
        imageView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        addFragment(R.id.container, SurfaceFragment.newInstance(MainActivity.this::onCloseGame));
    }

    @Override
    public void onCloseGame() {
        Log.d(TAG,"onCloseGame");
        removeFragment(SurfaceFragment.TAG);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment, fragment.getClass().getSimpleName())
                .commitNow();
    }

    protected void addToBackStackFragment(int containerViewId, Fragment fragment) {
        if (getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName()) == null)
            getSupportFragmentManager().beginTransaction()
                    .add(containerViewId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
    }

    protected void removeFragment(String tag) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}