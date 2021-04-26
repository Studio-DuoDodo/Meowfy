package com.example.meowtify;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.meowtify.activities.SplashActivity;
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.cert.PKIXCertPathChecker;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.sugar.Web.*;
import static androidx.test.espresso.web.webdriver.DriverAtoms.*;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.junit.Test;

public class LoginRegisterTest {
    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule= new ActivityScenarioRule<>(SplashActivity.class);

    @Test
    public void registerTest() {
        onView(withId(R.id.buttonLogin)).perform(click());
        onWebView().withElement(findElement(Locator.ID,""));
    }   @Test
    public void loginTest() {
        onView(withId(R.id.buttonLogin)).perform(click());

    }
    @Test
    public void loginWithSavedData() {
        onView(withId(R.id.buttonLogin)).perform(click());
         BaristaSleepInteractions.sleep(30, SECONDS);



    }

}
