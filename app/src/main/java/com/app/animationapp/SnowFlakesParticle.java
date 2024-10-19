package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class SnowFlakesParticle extends GameObject {

    public SnowFlakesParticle(Resources resources, float screenRatioX, float screenRatioY) {
        super();
        bitmap = getBitmap(resources, R.drawable.icon_snow_flakes);

        width = bitmap.getWidth() * 6;
        height = bitmap.getHeight() * 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public SnowFlakesParticle setSpawnX(float spawnX) {
        this.positionX = spawnX;
        return this;
    }

    public SnowFlakesParticle setSpawnCenterX(float spawnX) {
        this.positionX = spawnX - ( width / 2f);
        return this;
    }

    public SnowFlakesParticle setSpawnY(float spawnY) {
        this.positionY = spawnY;
        return this;
    }

    public SnowFlakesParticle setSpawnCenterY(float spawnY) {
        this.positionY = spawnY - (height / 2f);
        return this;
    }

    public SnowFlakesParticle build() {
        return this;
    }
}