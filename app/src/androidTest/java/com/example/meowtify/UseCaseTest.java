package com.example.meowtify;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.meowtify.activities.SplashActivity;

import org.junit.Rule;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.meowtify.NavigationTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest 
public class UseCaseTest {
    LoginRegisterTest loginRegisterTest = new LoginRegisterTest();
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void play_last_played_song() {
        loginRegisterTest.loginWithSavedData();
        ViewInteraction recyclerViewRecentlyPlayed = onView(
                allOf(withId(R.id.listaRecently),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)));
        recyclerViewRecentlyPlayed.perform(actionOnItemAtPosition(0, click()));

    }

    @Test
    public void follow_featured_playlist() {
        NavigationTest navigationTest = new NavigationTest();
        navigationTest.open_playlist_from_featured_playlist_of_home();
        ViewInteraction followButton = onView(
                allOf(withId(R.id.follow_playlist), withText("follow"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        followButton.perform(scrollTo(), click());
    }

    @Test
    public void enter_to_a_searched_artist() {
        NavigationTest navigationTest = new NavigationTest();
        navigationTest.home_to_search();
        ViewInteraction searchText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.search_edit),
                                0),
                        0),
                        isDisplayed()));
        searchText.perform(replaceText("Rick Astley"), closeSoftKeyboard());
        ViewInteraction resultList = onView(
                allOf(withId(R.id.lista_search),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)));
        resultList.perform(actionOnItemAtPosition(0, click()));
    }
}
