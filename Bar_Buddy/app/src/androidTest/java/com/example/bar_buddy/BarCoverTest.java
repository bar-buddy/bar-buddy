package com.example.bar_buddy;

import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bar_buddy.ManagerActivities.ManagerUpdateBarInfo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class BarCoverTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(ManagerUpdateBarInfo.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.input_barCover)).perform(typeText("$15")).check(matches(withText("$15")));
    }
}
