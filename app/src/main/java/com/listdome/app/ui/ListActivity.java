package com.listdome.app.ui;

import android.widget.TextView;

import butterknife.BindView;

import com.listdome.app.R;

/**
 * Created by raissa on 21/11/2017.
 */

public class ListActivity extends BaseActivity {

    private static final String TAG = ListActivity.class.getSimpleName();

    @BindView(R.id.message_list)
    protected TextView mTextMessage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_list;
    }

    @Override
    protected void configureLayout() {
        mTextMessage.setText("TODO List");
    }

}
