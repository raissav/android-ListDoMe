package com.listdome.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.listdome.app.R;
import com.listdome.app.infrastructure.Constants;
import com.listdome.app.ui.utils.SlideAnimationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by raissa on 23/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.navigation)
    protected BottomNavigationView navigationView;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        loadFirebase();
        configureToolBarAndNavigationView();
        animateTransaction();
        animateToolbar();
        loadElements();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
        updateDate();
        animateTransaction();
        animateToolbar();
        loadElements();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelOperations();
    }

    @Override
    public boolean onNavigationItemSelected(final @NonNull MenuItem item) {
        Log.v(TAG, "[method] onNavigationItemSelected: " + item.getItemId());

        switch (item.getItemId()) {

            case R.id.navigation_list:
                logEvent(Constants.Event.OPEN_TASKS, "");
                startActivity(new Intent(this, TaskActivity.class));
                return true;

            case R.id.navigation_ideas:
                logEvent(Constants.Event.OPEN_IDEAS, "");
                startActivity(new Intent(this, IdeasActivity.class));
                return true;

            case R.id.navigation_analysis:
                logEvent(Constants.Event.OPEN_ANALYTICS, "");
                startActivity(new Intent(this, AnalysisActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = getCurrentFocus();
            if (v instanceof EditText) {
                final Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * Load the Firebase Analytics
     */
    private void loadFirebase() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    /**
     * Configuration of Toolbar and Bottom Navigation View.
     */
    private void configureToolBarAndNavigationView() {
        Log.v(TAG, "[method] configureToolBarAndNavigationDrawer");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getMenuItemTitle());

        updateDate();

        navigationView.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Get the current date to be displayed at the toolbar.
     */
    private void updateDate() {
        final SimpleDateFormat format = new SimpleDateFormat("  dd, MMM yyyy", Locale.US);
        final String date = format.format(new Date());
        getSupportActionBar().setSubtitle(date);
    }

    /**
     * Animates the toolbar title and subtitle.
     */
    private void animateToolbar() {
        if (toolbar.getChildCount() > 1) {
            final View view0 = toolbar.getChildAt(0);
            SlideAnimationUtils.slideInFromRight(this, view0);
            final View view1 = toolbar.getChildAt(1);
            SlideAnimationUtils.slideInFromRight(this, view1);
        }
    }

    /**
     * Updates the Navigation Bar state.
     */
    private void updateNavigationBarState() {
        Log.v(TAG, "[method] updateNavigationBarState");

        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    /**
     * Changes the selected menu item.
     * @param itemId
     */
    private void selectBottomNavigationBarItem(final int itemId) {
        Log.v(TAG, "[method] selectBottomNavigationBarItem: " + itemId);

        final Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            final MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    /**
     * Hide the keyboard
     * @param v
     */
    protected void hideKeyboard(final View v) {
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Generates Toast.
     * @param message
     */
    protected void generateToast(final String message) {
        Log.v(TAG, "[method] generateToast");

        int duration = Toast.LENGTH_LONG;
        final Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    /**
     * Log an event at Firebase Analytics.
     * @param event
     * @param value
     */
    protected void logEvent(final String event, final String value) {
        Log.v(TAG, "[method] logEvent");

        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.VALUE, value);
        mFirebaseAnalytics.logEvent(event, bundle);
    }

    protected abstract int getContentViewId();

    protected abstract int getNavigationMenuItemId();

    protected abstract String getMenuItemTitle();

    protected abstract void loadElements();

    protected abstract void cancelOperations();

    protected abstract void animateTransaction();

}
