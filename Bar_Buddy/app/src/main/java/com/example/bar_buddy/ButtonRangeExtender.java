package com.example.bar_buddy;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class ButtonRangeExtender {

    public ButtonRangeExtender(final Button button, final int top, final int right, final int bottom, final int left) {
        final View parent = (View) button.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                button.getHitRect(rect);
                rect.top -= top;    // increase top hit area
                rect.left -= right;   // increase left hit area
                rect.bottom += bottom; // increase bottom hit area
                rect.right += right;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , button));
            }
        });
    }

    public ButtonRangeExtender(final ImageButton button, final int top, final int right, final int bottom, final int left) {
        final View parent = (View) button.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                button.getHitRect(rect);
                rect.top -= top;    // increase top hit area
                rect.left -= right;   // increase left hit area
                rect.bottom += bottom; // increase bottom hit area
                rect.right += right;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , button));
            }
        });
    }

    public ButtonRangeExtender(final ToggleButton button, final int top, final int right, final int bottom, final int left) {
        final View parent = (View) button.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                button.getHitRect(rect);
                rect.top -= top;    // increase top hit area
                rect.left -= right;   // increase left hit area
                rect.bottom += bottom; // increase bottom hit area
                rect.right += right;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , button));
            }
        });
    }
}
