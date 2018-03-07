package com.ayogeshwaran.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ayogeshwaran.bakingapp.Ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    private static final String STRING_TO_CHECK = "Cheesecake";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickRecyclerView_checkRecipe() throws InterruptedException {
        Thread.sleep(3000);
        // First, scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipes_Recycler_View))
                .perform(RecyclerViewActions.scrollToPosition(3));

        // Match the text in an item below the fold and check that it's displayed.
        onView(withText(STRING_TO_CHECK)).check(matches(isDisplayed()));
    }
}
