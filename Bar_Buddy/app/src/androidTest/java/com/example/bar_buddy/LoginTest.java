package com.example.bar_buddy;

import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bar_buddy.Activities.LoginActivity;
import com.example.bar_buddy.Activities.MainActivity;
import com.example.bar_buddy.Activities.SignupActivity;
import com.example.bar_buddy.Activities.UserTypeActivity;
import com.example.bar_buddy.ManagerActivities.ManagerMainActivity;
import com.google.firebase.firestore.auth.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Before
    public void setUp() {
        //Intents.init();
    }

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule =
        new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void loginAsManager() throws Exception {
        mLoginActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.email)).perform(typeText("manager@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(4000L);
        intended(hasComponent(ManagerMainActivity.class.getName()));
    }

    @Test
    public void loginAsCustomer() throws Exception {
        mLoginActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.email)).perform(typeText("swmaryland@crimson.ua.edu"));
        onView(withId(R.id.password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(4000L);
        intended(hasComponent(MainActivity.class.getName()));
    }

    @After
    public void tearDown() {
        //Intents.release();
    }
}
