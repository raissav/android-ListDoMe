package com.listdome.app.ui;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.listdome.app.R;
import com.listdome.app.gateway.database.dao.IdeaDao;
import com.listdome.app.helpers.CustomMatches;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.listdome.app.helpers.BaseHelper.addItem;
import static com.listdome.app.helpers.BaseHelper.clickBottomNavigationMenu;
import static com.listdome.app.helpers.BaseHelper.confirmDialog;
import static com.listdome.app.helpers.BaseHelper.validateEmptyCard;
import static com.listdome.app.helpers.BaseHelper.validateInspirationCard;
import static com.listdome.app.helpers.BaseHelper.validateListCard;
import static com.listdome.app.helpers.BaseHelper.validateToolbar;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class IdeaActivityTest {

    @Rule
    public ActivityTestRule<IdeasActivity> mActivityRule =
            new ActivityTestRule(IdeasActivity.class);

    @Before
    @After
    public void cleanupBefore() {
       final IdeaDao ideaDao = new IdeaDao(getInstrumentation().getTargetContext());
        ideaDao.deleteAll();
    }

    @Test
    public void completeFlowTask() {

        SystemClock.sleep(2500);

        //Changes to Analysis view
        clickBottomNavigationMenu(R.id.navigation_ideas, 1);

        //Validate all fields (empty)
        validateFields();

        //Add 1 idea
        addIdea();

        //Save inspiration
        saveInspiration();

        //Remove the idea
        removeIdea();
    }

    private void validateFields() {

        SystemClock.sleep(2500);

        //Validate toolbar
        validateToolbar(R.string.title_ideas);

        validateInspirationCard(R.id.image_inspiration, R.id.title_inspiration,
                R.id.text_inspiration, R.string.ideas_card_inspiration, R.id.save_inspiration);

        //Validate Idea List Card
        validateEmptyCard(R.id.card_ideas, R.id.title_ideas, R.string.ideas_card_list,
                R.id.list_ideas, R.id.new_idea, R.id.add_idea);
    }

    private void addIdea() {

        SystemClock.sleep(1000);

        //Add 1 idea
        addItem("Idea 1", R.id.new_idea, R.id.add_idea);

        //Validate list size
        validateListCard(R.id.list_ideas, R.id.marker_idea, R.id.text_idea,1);
    }

    private void saveInspiration() {

        SystemClock.sleep(1000);

        onView(withId(R.id.save_inspiration)).perform(click());

        //Validate list size
        validateListCard(R.id.list_ideas, R.id.marker_idea, R.id.text_idea,2);
    }

    private void removeIdea() {

        SystemClock.sleep(1000);

        onView(withId(R.id.list_ideas)).perform(scrollToPosition(0));

        confirmDialog(1, R.id.list_ideas, R.id.text_idea, R.string.remove_idea_dialog_title);
        confirmDialog(0, R.id.list_ideas, R.id.text_idea, R.string.remove_idea_dialog_title);

        //Validate To Do list size
        onView(withId(R.id.list_ideas)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list_ideas)).check(matches(CustomMatches.withRecyclerCount(0)));
    }

}
