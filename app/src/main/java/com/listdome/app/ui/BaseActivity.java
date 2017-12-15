package com.listdome.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.listdome.app.R;
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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        animateTransaction();
        configureToolBarAndNavigationView();
        loadElements();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
        animateTransaction();
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
                startActivity(new Intent(this, TaskActivity.class));
                return true;

            case R.id.navigation_ideas:
                startActivity(new Intent(this, IdeasActivity.class));
                return true;

            case R.id.navigation_analysis:
                startActivity(new Intent(this, AnalysisActivity.class));
                return true;
        }
        return false;
    }

    /**
     * Configuration of Toolbar and Bottom Navigation View.
     */
    private void configureToolBarAndNavigationView() {
        Log.v(TAG, "[method] configureToolBarAndNavigationDrawer");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getMenuItemTitle());

        final SimpleDateFormat format = new SimpleDateFormat("  dd, MMM yyyy", Locale.US);
        final String date = format.format(new Date());
        getSupportActionBar().setSubtitle(date);

        if (toolbar.getChildCount() > 1) {
            final View view0 = toolbar.getChildAt(0);
            SlideAnimationUtils.slideInFromRight(this, view0);
            final View view1 = toolbar.getChildAt(1);
            SlideAnimationUtils.slideInFromRight(this, view1);
        }

        navigationView.setOnNavigationItemSelectedListener(this);
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

    protected abstract int getContentViewId();

    protected abstract int getNavigationMenuItemId();

    protected abstract String getMenuItemTitle();

    protected abstract void loadElements();

    protected abstract void cancelOperations();

    protected abstract void animateTransaction();

}
