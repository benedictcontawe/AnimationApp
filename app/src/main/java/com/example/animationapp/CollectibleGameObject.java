package com.example.animationapp;

import android.graphics.Rect;

public class CollectibleGameObject extends GameObject {
    protected boolean isCollected;

    protected CollectibleGameObject() {
        super();
        isCollected = false;
    }

    protected void onTriggerCollide(Rect boxCollider2D) {
        if(Rect.intersects(boxCollider2D, getCollisionShape()) && isCollected == false) {
            isCollected = true;
        }
    }

    protected boolean onTriggerCollide(Rect boxCollider2D, int delay) {
        return Rect.intersects(boxCollider2D, getCollisionShape(delay));
    }
}