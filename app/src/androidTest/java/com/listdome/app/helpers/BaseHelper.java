package com.listdome.app.helpers;

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.listdome.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static com.listdome.app.helpers.CustomMatches.childAtPosition;
import static com.listdome.app.helpers.CustomMatches.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class BaseHelper {

    public static void validateToolbar(final int textResId) {
        final SimpleDateFormat format = new SimpleDateFormat("  dd, MMM yyyy", Locale.US);
        final String date = format.format(new Date());

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(textResId)).check(matches(withParent(withId(R.id.toolbar))));
        onView(withText(date)).check(matches(withParent(withId(R.id.toolbar))));
    }

    public static void validateBottomNavigationMenu(final int menuId, final int position) {

        onView(withId(R.id.navigation)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_list)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_ideas)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_analysis)).check(matches(isDisplayed()));

        clickBottomNavigationMenu(menuId, position);
    }

    public static void validateEmptyCard(final int cardId, final int titleId, final int titleTextId,
                                    final int listId, final int emptyTextId) {

        validateEmptyCard(cardId, titleId, titleTextId, listId);
        onView(withId(emptyTextId)).check(matches(isDisplayed()));
    }

    public static void validateEmptyCard(final int cardId, final int titleId, final int titleTextId,
                                    final int listId, final int editTextId, final int buttonId) {

        validateEmptyCard(cardId, titleId, titleTextId, listId);
        onView(withId(editTextId)).check(matches(isDisplayed()));
        onView(withId(editTextId)).check(matches(withText("")));
        onView(withId(buttonId)).check(matches(isDisplayed()));
    }

    public static void validateEmptyCard(final int cardId, final int titleId, final int titleTextId,
                                         final int listId) {

        onView(withId(cardId)).check(matches(isDisplayed()));
        onView(withId(titleId)).check(matches(isDisplayed()));
        onView(withId(titleId)).check(matches(withText(titleTextId)));
        onView(withId(titleId)).check(matches(hasTextColor(R.color.lightGrey)));
        onView(withId(listId)).check(matches(not(isDisplayed())));
        onView(withId(listId)).check(matches(CustomMatches.withRecyclerCount(0)));
    }

    public static void validateListCard(final int listId, final int checkBoxId, final int taskTextId,
                                        final int importantId, final int listSize, final int emptyTextId) {

        validateListCard(listId, checkBoxId, taskTextId, importantId, listSize);
        onView(withId(emptyTextId)).check(matches(not(isDisplayed())));
    }

    public static void validateListCard(final int listId, final int checkBoxId, final int taskTextId,
                                        final int importantId, final int listSize) {

        onView(withId(listId)).check(matches(isDisplayed()));
        onView(withId(listId)).check(matches(CustomMatches.withRecyclerCount(listSize)));

        for (int i = 0; i < listSize; i++) {
            onView(withId(listId)).perform(scrollToPosition(i));

            onView(withRecyclerView(listId).atPosition(i))
                .check(matches(hasDescendant(withId(checkBoxId))));
            onView(withRecyclerView(listId).atPosition(i))
                .check(matches(hasDescendant(withId(taskTextId))));
            onView(withRecyclerView(listId).atPosition(i))
                .check(matches(hasDescendant(withId(importantId))));
        }
    }

    public static void validateAnalysisCard(final int cardId, final int titleId, final int valueId,
                                            final String title, final String value) {

        onView(withId(cardId)).check(matches(isDisplayed()));

        onView(withId(titleId)).check(matches(isDisplayed()));
        onView(withId(titleId)).check(matches(withText(title + " PRODUCTIVITY")));
        onView(withId(titleId)).check(matches(hasTextColor(R.color.lightGrey)));

        onView(withId(valueId)).check(matches(isDisplayed()));
        onView(withId(valueId)).check(matches(withText("0 tasks / " + value)));
    }

    public static void addTask(final String task) {

        onView(withId(R.id.new_task)).perform(click());
        final ViewInteraction textInputSearchFilterText = onView(withId(R.id.new_task));
        textInputSearchFilterText.perform(replaceText(task), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.new_task)).check(matches(withText(task)));
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.new_task)).check(matches(withText("")));
    }

    public static void confirmDialog(final int position, final int listId, final int textClickId,
                               final int dialogTitleId) {

        onView(withId(listId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        position, CustomMatches.longClickChildViewWithId(textClickId)));

        SystemClock.sleep(1000);

        onView(withText(dialogTitleId)).check(matches(isDisplayed()));
        onView(withText(android.R.string.yes)).perform(click());
    }

    public static void clickBottomNavigationMenu(final int menuId, final int position) {
        final ViewInteraction bottomNavigationItemView = onView(allOf(withId(menuId),
                childAtPosition(childAtPosition(withId(R.id.navigation),0), position),
                isDisplayed()));
        bottomNavigationItemView.perform(click());
    }

}
