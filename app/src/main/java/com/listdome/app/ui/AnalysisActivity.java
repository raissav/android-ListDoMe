package com.listdome.app.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.listdome.app.R;
import com.listdome.app.infrastructure.operation.OperationError;
import com.listdome.app.infrastructure.operation.OperationListener;
import com.listdome.app.manager.AnalysisManager;
import com.listdome.app.ui.utils.SlideAnimationUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by raissa on 25/11/2017.
 */

public class AnalysisActivity extends BaseActivity {

    private static final String TAG = AnalysisActivity.class.getSimpleName();

    @BindView(R.id.container_analysis)
    protected ConstraintLayout containerAnalysis;

    @BindView(R.id.card_quantity_day)
    protected CardView cardQuantityDay;

    @BindView(R.id.title_quantity_day)
    protected TextView txtTitleQuantityDay;

    @BindView(R.id.value_quantity_day)
    protected TextView txtValueQuantityDay;

    @BindView(R.id.card_quantity_week)
    protected CardView cardQuantityWeek;

    @BindView(R.id.title_quantity_week)
    protected TextView txtTitleQuantityWeek;

    @BindView(R.id.value_quantity_week)
    protected TextView txtValueQuantityWeek;

    @BindView(R.id.card_quantity_month)
    protected CardView cardQuantityMonth;

    @BindView(R.id.title_quantity_month)
    protected TextView txtTitleQuantityMonth;

    @BindView(R.id.value_quantity_month)
    protected TextView txtValueQuantityMonth;

    protected AnalysisManager mAnalysisManager;

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
        Log.v(TAG, "[method] loadElements");

        mAnalysisManager = new AnalysisManager(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        loadCards();
        getDailyTaskAnalysis();
        getWeeklyTaskAnalysis();
        getMonthlyTaskAnalysis();
        animateCards();
    }

    @Override
    protected void cancelOperations() {
        Log.v(TAG, "[method] cancelOperations");

        if (mAnalysisManager != null) {
            mAnalysisManager.cancelOperations();
        }
    }

    @Override
    protected void animateTransaction() {
        SlideAnimationUtils.slideInFromRight(this, containerAnalysis);
    }

    /**
     * Load the card titles.
     */
    private void loadCards() {
        txtTitleQuantityDay.setText(getString(R.string.analysis_card_productivity, getString(R.string.daily)));
        txtTitleQuantityWeek.setText(getString(R.string.analysis_card_productivity, getString(R.string.weekly)));
        txtTitleQuantityMonth.setText(getString(R.string.analysis_card_productivity, getString(R.string.monthly)));

        txtValueQuantityDay.setText("");
        txtValueQuantityWeek.setText("");
        txtValueQuantityMonth.setText("");
    }

    /**
     * Get the daily task analysis from the DB.
     */
    private void getDailyTaskAnalysis() {
        Log.v(TAG, "[method] getDailyTaskAnalysis");

        mAnalysisManager.getDailyDoneTasks(new OperationListener<Integer>() {

            @Override
            public void onSuccess(final Integer result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);
                txtValueQuantityDay.setText(
                        getResources().getQuantityString(R.plurals.analysis_card_tasks_daily, result, result));
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);
                txtValueQuantityDay.setText(R.string.analysis_card_no_result);
            }
        });
    }

    /**
     * Get the weekly task analysis from the DB.
     */
    private void getWeeklyTaskAnalysis() {
        Log.v(TAG, "[method] getWeeklyTaskAnalysis");

        mAnalysisManager.getWeeklyDoneTasks(new OperationListener<Integer>() {

            @Override
            public void onSuccess(final Integer result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);
                txtValueQuantityWeek.setText(
                        getResources().getQuantityString(R.plurals.analysis_card_tasks_weekly, result, result));
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);
                txtValueQuantityWeek.setText(R.string.analysis_card_no_result);
            }
        });
    }

    /**
     * Get the monthly task analysis from the DB.
     */
    private void getMonthlyTaskAnalysis() {
        Log.v(TAG, "[method] getMonthlyTaskAnalysis");

        mAnalysisManager.getMonthlyDoneTasks(new OperationListener<Integer>() {

            @Override
            public void onSuccess(final Integer result) {
                Log.v(TAG, "[condition] onSuccess");
                super.onSuccess(result);
                txtValueQuantityMonth.setText(
                        getResources().getQuantityString(R.plurals.analysis_card_tasks_monthly, result, result));
            }

            @Override
            public void onError(final List<OperationError> errors) {
                Log.v(TAG, "[condition] onError");
                super.onError(errors);
                txtValueQuantityMonth.setText(R.string.analysis_card_no_result);
            }
        });
    }

    private void animateCards() {
        Log.v(TAG, "[method] animateLogo");

        scaleDown(cardQuantityDay, 600);
        scaleDown(cardQuantityWeek, 725);
        scaleDown(cardQuantityMonth, 850);
    }

    private void scaleDown(final CardView view, final long delay) {
        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.12f),
                PropertyValuesHolder.ofFloat("scaleY", 1.12f));

        scaleDown.setDuration(300);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setRepeatCount(1);
        scaleDown.setStartDelay(delay);
        scaleDown.start();
    }
}
