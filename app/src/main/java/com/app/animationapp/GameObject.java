package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class GameObject {

    protected Bitmap bitmap;
    protected int width, height, spawnDelay, positionX, positionY;

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
        Drawable drawable = resources.getDrawable(drawableRes); //ResourcesCompat.getDrawable(resources, drawableRes, theme);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    protected Rect getCollisionShape () {
        return new Rect(positionX, positionY, positionX + width, positionY + height);
    }

    protected Rect getCollisionShape (int delay) {
        return new Rect(positionX, delay, positionX + width, delay + height);
    }
}