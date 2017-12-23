package com.listdome.app.ui;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.listdome.app.R;
import com.listdome.app.gateway.database.dao.TaskDao;
import com.listdome.app.helpers.CustomMatches;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.listdome.app.helpers.BaseHelper.addTask;
import static com.listdome.app.helpers.BaseHelper.clickBottomNavigationMenu;
import static com.listdome.app.helpers.BaseHelper.confirmDialog;
import static com.listdome.app.helpers.BaseHelper.validateAnalysisCard;
import static com.listdome.app.helpers.BaseHelper.validateBottomNavigationMenu;
import static com.listdome.app.helpers.BaseHelper.validateEmptyCard;
import static com.listdome.app.helpers.BaseHelper.validateListCard;
import static com.listdome.app.helpers.BaseHelper.validateToolbar;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class AnalysisActivityTest {

    @Rule
    public ActivityTestRule<AnalysisActivity> mActivityRule =
            new ActivityTestRule(AnalysisActivity.class);

    @Before
    @After
    public void cleanupBefore() {
       final TaskDao taskDao = new TaskDao(getInstrumentation().getTargetContext());
       taskDao.deleteAll();
    }

    @Test
    public void completeFlowTask() {

        SystemClock.sleep(2500);

        //Changes to Analysis view
        clickBottomNavigationMenu(R.id.navigation_analysis, 2);

        //Validate all fields (empty)
        validateFields();
    }

    private void validateFields() {

        SystemClock.sleep(1000);

        //Validate toolbar
        validateToolbar(R.string.title_analysis);

        validateAnalysisCard(R.id.card_quantity_day, R.id.title_quantity_day,
                R.id.value_quantity_day, "DAILY", "day");

        validateAnalysisCard(R.id.card_quantity_week, R.id.title_quantity_week,
                R.id.value_quantity_week,"WEEKLY", "week");

        validateAnalysisCard(R.id.card_quantity_month, R.id.title_quantity_month,
                R.id.value_quantity_month,"MONTHLY", "month");
    }

}
