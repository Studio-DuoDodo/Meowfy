package com.example.meowtify;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.meowtify.activities.SplashActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.cert.PKIXCertPathChecker;
import java.sql.Time;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.sugar.Web.*;
import static androidx.test.espresso.web.webdriver.DriverAtoms.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import org.junit.Rule;
import org.junit.Test;

public class NavigationTest {
    LoginRegisterTest loginRegisterTest = new LoginRegisterTest();
    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule= new ActivityScenarioRule<>(SplashActivity.class);

    @Test
    public void home_to_search() {
        loginRegisterTest.loginWithSavedData();
        ViewInteraction toSearch = onView(
                allOf(withId(R.id.share), withContentDescription("search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigation),
                                        0),
                                1),
                        isDisplayed()));
         toSearch.perform( click());}

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void home_to_library() {
        loginRegisterTest.loginWithSavedData();
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.library), withContentDescription("your library"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());;
    }

    @Test
    public void open_playlist_from_featured_playlist_of_home() {
        loginRegisterTest.loginWithSavedData();
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.listJumpBack),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                6)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
    }
}
