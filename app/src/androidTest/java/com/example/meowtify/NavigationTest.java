package com.example.meowtify;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.meowtify.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainToSearch() {
        onView(withId(R.id.sheare))
                .perform(click());

        onView(withId(R.id.search_edit)).check(matches(isEnabled()));
    }

    @Test
    public void mainToLibrary() {
        onView(withId(R.id.library))
                .perform(click());

        onView(withId(R.id.secctionTitel)).check(matches(isEnabled()));
    }

    @Test
    public void mainToReproductor() {
        onView(withId(R.id.listaRecently))
                .perform(click());

        onView(withId(R.id.playButton)).check(matches(isEnabled()));
    }
}
