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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.listdome.app.helpers.BaseHelper.addItem;
import static com.listdome.app.helpers.BaseHelper.confirmDialog;
import static com.listdome.app.helpers.BaseHelper.validateBottomNavigationMenu;
import static com.listdome.app.helpers.BaseHelper.validateEmptyCard;
import static com.listdome.app.helpers.BaseHelper.validateListCard;
import static com.listdome.app.helpers.BaseHelper.validateToolbar;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TaskActivityTest {

    @Rule
    public ActivityTestRule<TaskActivity> mActivityRule =
            new ActivityTestRule(TaskActivity.class);

    @Before
    @After
    public void cleanupBefore() {
       final TaskDao taskDao = new TaskDao(getInstrumentation().getTargetContext());
       taskDao.deleteAll();
    }

    @Test
    public void completeFlowTask() {

        //Validate all fields (empty)
        validateFields();

        //Add 2 tasks and mark one of them as important
        todoTask();

        //Mark both tasks to In Progress status
        inProgressTask();

        //Stop one of the tasks
        stopTask();

        //Mark one tasks to Done status
        doneTask();

        //Remove the tasks Done
        removeTask();
    }

    private void validateFields() {

        SystemClock.sleep(2500);

        //Validate toolbar
        validateToolbar(R.string.title_tasks);

        //Validate Bottom Navigation
        validateBottomNavigationMenu(R.id.navigation_list, 0);

        //Validate First Card - TODO
        validateEmptyCard(R.id.card_todo, R.id.title_todo, R.string.task_card_todo,
                R.id.list_todo, R.id.new_task, R.id.add_task);

        //Validate Second Card - DOING
        validateEmptyCard(R.id.card_doing, R.id.title_doing, R.string.task_card_doing,
                R.id.list_doing, R.id.empty_view_doing);

        //Validate Third Card - DONE
        validateEmptyCard(R.id.card_done, R.id.title_done, R.string.task_card_done,
                R.id.list_done, R.id.empty_view_done);
    }

    private void todoTask() {

        SystemClock.sleep(1000);

        //Add 2 tasks
        addItem("Task 1", R.id.new_task, R.id.add_task);
        addItem("Task 1", R.id.new_task, R.id.add_task);

        //Validate list size
        validateListCard(R.id.list_todo, R.id.check_todo_task, R.id.text_todo_task,
                R.id.important_todo_task, 2);

        //Mark the 1st as important
        onView(withId(R.id.list_todo)).perform(
            RecyclerViewActions.actionOnItemAtPosition(
                0, CustomMatches.clickChildViewWithId(R.id.important_todo_task)));
    }

    private void inProgressTask() {

        SystemClock.sleep(1000);

        onView(withId(R.id.list_todo)).perform(scrollToPosition(0));

        //Mark both tasks to In Progress status
        onView(withId(R.id.list_todo)).perform(
            RecyclerViewActions.actionOnItemAtPosition(
                1, CustomMatches.clickChildViewWithId(R.id.check_todo_task)));
        onView(withId(R.id.list_todo)).perform(
            RecyclerViewActions.actionOnItemAtPosition(
                0, CustomMatches.clickChildViewWithId(R.id.check_todo_task)));

        //Validate To Do list size
        onView(withId(R.id.list_todo)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list_todo)).check(matches(CustomMatches.withRecyclerCount(0)));

        //Validate Doing list size
        validateListCard(R.id.list_doing, R.id.check_doing_task, R.id.text_doing_task,
                R.id.important_doing_task, 2);
    }

    private void stopTask() {

        SystemClock.sleep(1000);

        onView(withId(R.id.list_doing)).perform(scrollToPosition(0));

        //Return first tasks to In Progress status
        confirmDialog(1, R.id.list_doing, R.id.text_doing_task, R.string.stop_task_dialog_title);

        //Validate To Do list size
        onView(withId(R.id.list_todo)).check(matches(isDisplayed()));
        onView(withId(R.id.list_todo)).check(matches(CustomMatches.withRecyclerCount(1)));

    }

    private void doneTask() {

        SystemClock.sleep(1000);

        onView(withId(R.id.list_doing)).perform(scrollToPosition(0));

        //Mark first tasks to Done status
        onView(withId(R.id.list_doing)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        0, CustomMatches.clickChildViewWithId(R.id.check_doing_task)));

        //Validate Doing list size
        onView(withId(R.id.list_doing)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list_doing)).check(matches(CustomMatches.withRecyclerCount(0)));
        onView(withId(R.id.empty_view_doing)).check(matches(isDisplayed()));

        //Validate Done list size
        validateListCard(R.id.list_done, R.id.check_done_task, R.id.text_done_task,
                R.id.important_done_task, 1);
    }

    private void removeTask() {

        SystemClock.sleep(1000);

        onView(withId(R.id.list_done)).perform(scrollToPosition(0));

        confirmDialog(0, R.id.list_done, R.id.text_done_task, R.string.remove_task_dialog_title);

        //Validate To Do list size
        onView(withId(R.id.list_done)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list_done)).check(matches(CustomMatches.withRecyclerCount(0)));
        onView(withId(R.id.empty_view_done)).check(matches(isDisplayed()));

    }

}
