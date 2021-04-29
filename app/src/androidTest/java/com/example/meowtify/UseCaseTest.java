package com.example.meowtify;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.fragments.ArtistFragment;

import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class UseCaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void pesonalMissatgeCheck() {
        ViewInteraction text = onView(withId(R.id.missatgePersonalitzat));

        int date = Integer.parseInt(new SimpleDateFormat("H", Locale.UK).format(new Date().getTime()));
        AtomicReference<String> s = new AtomicReference<>("");
        activityRule.getScenario().onActivity(activity -> s.set(activity.getSharedPreferences("SPOTIFY", 0).getString("username", "Unknown user")));

        if (8 > date) {
            text.check(matches(withText("Too early " + s)));
        } else if (12 > date) {
            text.check(matches(withText("Good morning " + s)));
        } else if (15 > date) {
            text.check(matches(withText("Good noon " + s)));
        } else if (21 > date) {
            text.check(matches(withText("Good afternoon " + s)));
        } else {
            text.check(matches(withText("Good evening " + s)));
        }
    }

    @Test
    public void FollowButtonCheck() {
        NavigationTest navigationTest = new NavigationTest();
        navigationTest.mainToAlbum();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ArtistFragment(), "Artist").commit());

        ViewInteraction favbutton = onView(withId(R.id.follow_artist));

        /*if(favbutton.check(withText("follow")))){
            favbutton.perform(click()).check(matches(withText("unfollow")));
        }else{
            favbutton.perform(click()).check(matches(withText("follow")));
        }*/
    }

    @Test
    public void shearchInformationcheck() {
        new NavigationTest().mainToSearch();

        onView(withId(R.id.search_edit))
                .perform(replaceText("pets"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.lista_search))
                .check(matches(isEnabled()));
    }
}
