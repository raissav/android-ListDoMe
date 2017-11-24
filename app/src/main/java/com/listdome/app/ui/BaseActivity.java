package com.listdome.app.ui;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by raissa on 23/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    /**
     * Hides the status bar.
     */
    protected void hideStatusBar() {
        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
