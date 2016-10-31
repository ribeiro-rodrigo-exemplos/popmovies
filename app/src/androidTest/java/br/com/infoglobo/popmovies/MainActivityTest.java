package br.com.infoglobo.popmovies;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.intent.Intents.intended;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;

/**
 * Created by rodrigo on 30/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(MainActivity.class,true,true);

    @Test
    public void showMoviesList(){

        onView(withId(R.id.movies_list)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecyclerView(){

        Intents.init();

        onView(
                withId(R.id.movies_list)
              )
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));


       intended(hasComponent(DetailsActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void checkTitle(){
        Assert.assertEquals("PopMovies",activityRule.getActivity().getTitle());
    }
}
