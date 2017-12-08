package com.listdome.app.ui;

import android.widget.TextView;

import com.listdome.app.R;

import butterknife.BindView;

/**
 * Created by raissa on 25/11/2017.
 */

public class AnalysisActivity extends BaseActivity {

    private static final String TAG = AnalysisActivity.class.getSimpleName();

    @BindView(R.id.message_analysis)
    protected TextView mTextMessage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_analysis;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_analysis;
    }

    @Override
    protected String getMenuItemTitle() {
        return getString(R.string.title_analysis);
    }

    @Override
    protected void loadElements() {
        mTextMessage.setText("Analysis");
    }

    @Override
    protected void cancelOperations() {
    }

}
