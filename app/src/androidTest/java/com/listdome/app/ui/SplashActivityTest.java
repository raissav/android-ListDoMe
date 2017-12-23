package com.listdome.app.ui;

import android.os.SystemClock;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.listdome.app.R;
import com.listdome.app.ui.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule =
            new ActivityTestRule(SplashActivity.class);

    @Test
    public void validateFields() {
        SystemClock.sleep(500);
        onView(ViewMatchers.withId(R.id.splash_logo_name)).check(matches(isDisplayed()));
        onView(withId(R.id.splash_logo_img)).check(matches(isDisplayed()));
    }
}
