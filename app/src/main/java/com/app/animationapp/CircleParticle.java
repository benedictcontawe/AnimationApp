package com.app.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class CircleParticle extends ToggleGameObject {
    public CircleParticle(Resources resources, float screenRatioX, float screenRatioY) {
        super();
        bitmap = getBitmap(resources, R.drawable.icon_incomplete_circle);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public void updateBitMap(Resources resources) {
        if (isToggled && bitmap != getBitmap(resources, R.drawable.icon_circle)) {
            bitmap = getBitmap(resources, R.drawable.icon_circle);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        } else if (isToggled == false && bitmap != getBitmap(resources, R.drawable.icon_incomplete_circle)) {
            bitmap = getBitmap(resources, R.drawable.icon_incomplete_circle);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }
        /*
        if (isToggled) {
            bitmap = getBitmap(resources, R.drawable.icon_circle);
        } else {
            bitmap = getBitmap(resources, R.drawable.icon_incomplete_circle);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        */
    }

    public CircleParticle setSpawnCenterX(float spawnX) {
        this.positionX = spawnX - (width / 2f);
        return this;
    }

    public CircleParticle setSpawnCenterY(float spawnY) {
        this.positionY = spawnY - (height / 2f);
        return this;
    }

    public CircleParticle build() {
        return this;
    }
}