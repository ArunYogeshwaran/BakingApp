package com.ayogeshwaran.bakingapp;

import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ayogeshwaran.bakingapp.Matcher.RecyclerViewMatcher;
import com.ayogeshwaran.bakingapp.Ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getSimpleIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void idlingResourceTest() throws InterruptedException {
        Thread.sleep(2000);

        onView(withRecyclerView(R.id.recipes_Recycler_View).atPosition(0))
                .check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.recipes_Recycler_View).atPositionOnView(
                0, R.id.recipe_name_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText("Nutella Pie")));

        onView(withRecyclerView(R.id.recipes_Recycler_View).atPosition(0))
                .perform(click());
    }

    public RecyclerViewMatcher withRecyclerView(@IdRes int id) {
        return new RecyclerViewMatcher(id);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
