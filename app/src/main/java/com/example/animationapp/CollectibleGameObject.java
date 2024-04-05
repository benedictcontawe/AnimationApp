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
}