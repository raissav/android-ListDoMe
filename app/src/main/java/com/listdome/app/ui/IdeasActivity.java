package com.listdome.app.ui;

import android.widget.TextView;

import com.listdome.app.R;

import butterknife.BindView;

/**
 * Created by raissa on 25/11/2017.
 */

public class IdeasActivity extends BaseActivity {

    private static final String TAG = IdeasActivity.class.getSimpleName();

    @BindView(R.id.message_ideas)
    protected TextView mTextMessage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ideas;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_ideas;
    }

    @Override
    protected void configureLayout() {
        mTextMessage.setText("Ideas");
    }

}
