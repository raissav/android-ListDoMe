package com.listdome.app.ui.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;

/**
 * Created by raissa on 15/12/2017.
 */

public final class ColorAnimationUtils {

    public static void animateView(final View view, final int colorBlink, final int regularColor) {

        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.05f),
                PropertyValuesHolder.ofFloat("scaleY", 1.05f));

        scaleDown.setDuration(200);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setRepeatCount(1);

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(final Animator animation) {
                blinkBackground(view, colorBlink, regularColor);
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
            }

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }
        });

        scaleDown.start();
    }

    public static void blinkBackground(final View view, final int colorBlink, final int regularColor) {
        final ColorDrawable[] color = {
                new ColorDrawable(colorBlink),
                new ColorDrawable(regularColor)
        };
        final TransitionDrawable transition = new TransitionDrawable(color);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(transition);
        } else {
            view.setBackground(transition);
        }
        transition.startTransition(500);
    }
}
