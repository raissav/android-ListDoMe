package com.listdome.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.listdome.app.R;


/**
 * Created by raissa on 23/11/2017.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private final static int MSG_CONTINUE = 2000;
    private final static long DELAY = 4000;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
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
                case MSG_CONTINUE:
                    startActivity();
                    return true;
            }
            return false;
        }
    });

    private void startActivity() {
        Log.v(TAG, "[method] startActivity: " + MainActivity.class.getSimpleName());
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
