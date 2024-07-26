package com.app.animationapp;

import android.graphics.Rect;

public class ToggleGameObject extends GameObject {
    protected boolean isToggled;

    protected ToggleGameObject() {
        super();
        isToggled = false;
    }

    protected void onTriggerCollide(Rect boxCollider2D) {
        if(Rect.intersects(boxCollider2D, getCollisionShape()) && isToggled == false) {
            isToggled = true;
        } else if (Rect.intersects(boxCollider2D, getCollisionShape()) && isToggled == true) {
            isToggled = false;
        }
    }

    protected boolean onTriggerCollide(Rect boxCollider2D, int delay) {
        return Rect.intersects(boxCollider2D, getCollisionShape(delay));
    }
}
