package yargo.inc.evil.yargo_3;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnitRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import yargo.inc.MainActivity;
import yargo.inc.R;
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Test
    public void test_login(){
        activityActivityTestRule.launchActivity(null);
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("login"));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button)).perform(ViewActions.click());

    }
}
