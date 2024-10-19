package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class PegParticle extends GameObject {
    public PegParticle(Resources resources, float screenRatioX, float screenRatioY) {
        super();
        bitmap = getBitmap(resources, R.drawable.icon_peg);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        //width = (int) (width * screenRatioX);
        //height = (int) (height * screenRatioY);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public PegParticle setSpawnX(float spawnX) {
        this.positionX = spawnX;
        return this;
    }

    public PegParticle setSpawnY(float spawnY) {
        this.positionY = spawnY;
        return this;
    }

    public PegParticle setSpawnCenterX(float spawnX) {
        this.positionX = spawnX - ( width / 2f);
        return this;
    }

    public PegParticle setSpawnCenterY(float spawnY) {
        this.positionY = spawnY - (height / 2f);
        return this;
    }

    public int getRadius() {
        return Math.min(width, height) / 2;
    }

    public PegParticle build() {
        return this;
    }
}