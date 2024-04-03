package com.example.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackgroundTileMap {
    public int x = 0, y = 0;
    public Bitmap bitmap;
    public BackgroundTileMap(int screenX, int screenY, Resources resources) {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.background);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenX, screenY, false);
    }
}