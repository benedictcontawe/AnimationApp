package com.example.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class GameObject {

    protected Bitmap bitmap;
    protected int width, height, spawnDelay, x, y;

    public GameObject() {
        /*
        width /= 3;
        height /= 3;
        */
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    protected Bitmap getBitmap(Resources resources, int drawableRes) {
        Drawable drawable = resources.getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}