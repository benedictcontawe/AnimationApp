package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class DropParticle extends GameObject {

    public DropParticle(Resources resources, float screenRatioX, float screenRatioY) {
        super();
        bitmap = getBitmap(resources, R.drawable.icon_water_drop);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public DropParticle setSpawnX(int spawnX) {
        this.positionX = spawnX;
        return this;
    }

    public DropParticle setSpawnY(int spawnY) {
        this.positionY = spawnY;
        return this;
    }

    public DropParticle setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
        return this;
    }

    public void updateSpawnDelay(int decrement) {
        if (!isSpawnDelayFinished()) {
            this.spawnDelay -= decrement;
        }
    }

    public boolean isSpawnDelayFinished() {
        return spawnDelay <= 0;
    }

    public DropParticle build() {
        return this;
    }
}