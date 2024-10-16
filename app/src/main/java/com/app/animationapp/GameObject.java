package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;

public class GameObject {

    protected Bitmap bitmap;
    protected int width, height, spawnDelay;
    protected float positionX, positionY;

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

    protected Bitmap getBitmap(Resources resources, int drawableRes, Resources.Theme theme) {
        Drawable drawable = ResourcesCompat.getDrawable(resources, drawableRes, theme);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    protected RectF getCollisionShape () {
        return new RectF(positionX, positionY, positionX + width, positionY + height);
    }

    protected RectF getCollisionShape (int delay) {
        return new RectF(positionX, delay, positionX + width, delay + height);
    }
}