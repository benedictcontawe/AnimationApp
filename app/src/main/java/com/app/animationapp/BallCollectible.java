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

    public void updatePosition(float gravity) {
        // Apply gravity
        velocityY += gravity;
        // Move the ball based on its velocity
        positionX += velocityX;
        positionY += velocityY;
    }

    public int getRadius() {
        return Math.min(width, height) / 2;
    }

    public boolean isColliding(BallCollectible ball) {
        float dx = positionX + getRadius() - (ball.positionX + ball.getRadius());
        float dy = positionY + getRadius() - (ball.positionY + ball.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (getRadius() + ball.getRadius());
    }

    public boolean isColliding(PegParticle peg) {
        float dx = positionX + getRadius() - (peg.positionX + peg.getRadius());
        float dy = positionY + getRadius() - (peg.positionY + peg.getRadius());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (getRadius() + peg.getRadius());
    }

    public BallCollectible build() {
        return this;
    }
}