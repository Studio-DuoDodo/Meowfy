package com.example.meowtify;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.meowtify.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class UseCaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void PlayButtonCheck() {
        onView(withId(R.id.playButton))
                .perform(click());


    }

}
