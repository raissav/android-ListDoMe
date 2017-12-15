package com.listdome.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.listdome.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by raissa on 23/11/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private final static int MSG_ANIMATE = 1000;
    private final static int MSG_CONTINUE = 2000;

    private final static long DELAY = 4000;

    @BindView(R.id.splash_logo_img)
    protected ImageView imgLogo;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        mHandler.sendEmptyMessageDelayed(MSG_ANIMATE, 0);
        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
        hideStatusBar();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mHandler.removeMessages(MSG_CONTINUE);
        super.onPause();
    }

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_ANIMATE:
                    animateLogo();
                    return true;
                case MSG_CONTINUE:
                    startActivity();
                    return true;
            }
            return false;
        }
    });

    /**
     * Starts List Activity
     */
    private void startActivity() {
        Log.v(TAG, "[method] startActivity: " + TaskActivity.class.getSimpleName());

        startActivity(new Intent(SplashActivity.this, TaskActivity.class));
        finish();
    }

    /**
     * Hides the status bar.
     */
    protected void hideStatusBar() {
        Log.v(TAG, "[method] hideStatusBar");

        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void animateLogo() {
        Log.v(TAG, "[method] animateLogo");

        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                imgLogo,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));

        scaleDown.setDuration(300);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setRepeatCount(3);

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                scaleDown.setStartDelay(1000);
                scaleDown.start();
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
}
