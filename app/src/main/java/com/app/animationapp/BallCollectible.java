package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class BallCollectible extends CollectibleGameObject {
    public float velocityX, velocityY;

    public BallCollectible(Resources resources, float screenRatioX, float screenRatioY) {
        super();
        bitmap = getBitmap(resources, R.drawable.icon_ball);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        //width = Math.round(screenRatioX * 0.05f);
        //height = Math.round(screenRatioX * 0.05f);

        this.velocityX = 0f;
        this.velocityY = 0f;

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public BallCollectible setSpawnX(float spawnX) {
        this.positionX = spawnX;
        return this;
    }

    public BallCollectible setSpawnY(float spawnY) {
        this.positionY = spawnY;
        return this;
    }

    public BallCollectible setSpawnDelay(int spawnDelay) {
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

    public int getRadius() {
        return Math.min(width, height) / 2;
    }

    public BallCollectible build() {
        return this;
    }
}