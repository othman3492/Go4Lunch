package com.othman.go4lunch;

import com.othman.go4lunch.controllers.activities.MainPageActivity;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class InstrumentedTest {

    @Rule
    public final ActivityTestRule<MainPageActivity>activityTestRule = new ActivityTestRule<>(MainPageActivity.class);


    @Test
    public void clickOnListView_opensListFragment() {

        onView(withId(R.id.bottom_list_view)).perform(click());
        onView(withId(R.id.restaurant_name)).check(matches(isDisplayed()));
    }


    @Test
    public void clickOnWorkmatesView_opensWorkmatesFragment() {

        onView(withId(R.id.bottom_workmates)).perform(click());
        onView(withId(R.id.workmates_text_view)).check(matches(isDisplayed()));
    }


    @Test
    public void clickOnYourLunchDrawer_opensRestaurantDetails() {

        onView(withId(R.id.main_page_drawer_lunch)).perform(click());
        onView(withId(R.id.floating_action_button)).check(matches(isDisplayed()));
    }


    @Test
    public void clickOnSettingsDrawer_opensSettingsActivity() {

        onView(withId(R.id.main_page_drawer_settings)).perform(click());
        onView(withId(R.id.delete_button)).check(matches(isDisplayed()));
    }


    @Test
    public void clickOnLogOutDrawer_opensRestaurantDetails() {

        onView(withId(R.id.main_page_drawer_logout)).perform(click());
        onView(withId(R.id.app_logo)).check(matches(isDisplayed()));
    }






}