package com.app.animationapp;

import android.graphics.RectF;

public class CollectibleGameObject extends GameObject {
    protected boolean isCollected;

    protected CollectibleGameObject() {
        super();
        isCollected = false;
    }

    protected void onTriggerCollide(RectF boxCollider2D) {
        if(RectF.intersects(boxCollider2D, getCollisionShape()) && isCollected == false) {
            isCollected = true;
        }
    }

    public boolean onTriggerCollide(RectF boxCollider2D, int delay) {
        return RectF.intersects(boxCollider2D, getCollisionShape(delay));
    }
}