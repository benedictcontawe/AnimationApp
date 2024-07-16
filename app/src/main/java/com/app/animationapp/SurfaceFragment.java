package com.app.animationapp;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SurfaceFragment extends Fragment {

    public static String TAG = SurfaceFragment.class.getSimpleName();
    public static SurfaceFragment newInstance(Listener listener) {
        SurfaceFragment fragment = new SurfaceFragment();
        fragment.listener = listener;
        return fragment;
    }

    private CustomSurfaceView customSurfaceView;
    private Listener listener;

    public SurfaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        customSurfaceView = CustomSurfaceView.newInstance(requireContext(), getWidth(), getHeight(), listener);
        return customSurfaceView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private int getWidth() {
        WindowManager windowManager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.d(TAG,"getWidth >= R");
            final WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
            final WindowInsets windowInsets = windowMetrics.getWindowInsets();
            final Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            final int insetsWidth = insets.right + insets.left;
            final Rect bounds = windowMetrics.getBounds();
            return bounds.width() - insetsWidth;
        } else {
            Log.d(TAG,"getWidth < R");
            final Point size = new Point();
            final Display display = windowManager.getDefaultDisplay(); // deprecated in API 30
            display.getSize(size); // deprecated in API 30
            return size.x;
        }
    }

    private int getHeight() {
        WindowManager windowManager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.d(TAG,"getWidth >= R");
            final WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
            //final WindowInsetsCompat windowInsetsCompat = ViewCompat.getRootWindowInsets(requireView());
            //final Insets insets = windowInsetsCompat.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.navigationBars() | WindowInsetsCompat.Type.displayCutout()).toPlatformInsets();
            final WindowInsets windowInsets = windowMetrics.getWindowInsets();
            final Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            final int insetsHeight = insets.top + insets.bottom;
            final Rect bounds = windowMetrics.getBounds();
            return bounds.height() - insetsHeight;
        } else {
            Log.d(TAG,"getWidth < R");
            final Point size = new Point();
            final Display display = windowManager.getDefaultDisplay(); // deprecated in API 30
            display.getSize(size); // deprecated in API 30
            return size.y;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        //customSurfaceView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        //customSurfaceView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}