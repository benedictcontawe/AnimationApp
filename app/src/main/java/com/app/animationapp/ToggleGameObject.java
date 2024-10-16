package com.app.animationapp;

import android.graphics.RectF;

public class ToggleGameObject extends GameObject {
    protected boolean isToggled;

    protected ToggleGameObject() {
        super();
        isToggled = false;
    }

    protected void onTriggerCollide(RectF boxCollider2D) {
        if(RectF.intersects(boxCollider2D, getCollisionShape()) && isToggled == false) {
            isToggled = true;
        } else if (RectF.intersects(boxCollider2D, getCollisionShape()) && isToggled == true) {
            isToggled = false;
        }
    }

    protected boolean onTriggerCollide(RectF boxCollider2D, int delay) {
        return RectF.intersects(boxCollider2D, getCollisionShape(delay));
    }
}
