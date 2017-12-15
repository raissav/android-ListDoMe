package com.listdome.app.ui;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toolbar;

import com.listdome.app.R;
import com.listdome.app.ui.utils.SlideAnimationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by raissa on 25/11/2017.
 */

public class IdeasActivity extends BaseActivity {

    private static final String TAG = IdeasActivity.class.getSimpleName();

    @BindView(R.id.container_ideas)
    protected ConstraintLayout containerIdeas;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ideas;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_ideas;
    }

    @Override
    protected String getMenuItemTitle() {
        return getString(R.string.title_ideas);
    }

    @Override
    protected void loadElements() {
    }

    @Override
    protected void cancelOperations() {
    }

    @Override
    protected void animateTransaction() {
        SlideAnimationUtils.slideInFromRight(this, containerIdeas);
    }

}
