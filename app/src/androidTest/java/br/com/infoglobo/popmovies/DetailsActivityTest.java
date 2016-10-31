package br.com.infoglobo.popmovies;

/**
 * Created by rodrigo on 30/10/16.
 */

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import br.com.infoglobo.popmovies.model.Movie;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<DetailsActivity> rule =
            new ActivityTestRule<DetailsActivity>(DetailsActivity.class,false,false);

    private Movie movie;

    @Before
    public void before(){

        movie = new Movie();
        movie.setId(188927);
        movie.setName("Star Trek Beyond");
        movie.setRating(6.33f);
        movie.setRuntime(122);
        movie.setLanguage("en");

        Intent intent = new Intent();
        intent.putExtra("movie", Parcels.wrap(movie));
        rule.launchActivity(intent);
        try{Thread.sleep(2000);}catch (InterruptedException e){}
    }

    @Test
    public void checkNameMovie(){

        onView(
                withId(R.id.movie_details_name)
              )
                    .check(
                            matches(isDisplayed())
                          );
    }

    @Test
    public void checkMovieGenre(){

        onView(
                withId(R.id.movie_details_runtime)
              )
                .check(
                        matches(isDisplayed())
                      );

    }

    @Test
    public void checkMovieRuntime(){

        onView(
                withId(R.id.movie_details_runtime)
              )
                .check(
                        matches(withText(String.format("%d min",movie.getRuntime())))
                      );
    }

    @Test
    public void checkMovieLanguage(){

        onView(
                withId(R.id.movie_details_language)
              )
                .check(
                        matches(withText(movie.getLanguage()))
                      );
    }

    @Test
    public void checkMovieImage(){

        onView(
                withId(R.id.movie_details_image)
              )
                .check(
                        matches(isDisplayed())
                      );
    }

    @Test
    public void checkMovieOverview(){

        onView(
                withId(R.id.movie_details_overview)
              )
                .check(
                        matches(isDisplayed())
                      );
    }

    @Test
    public void checkTitle(){

        Assert.assertEquals("Details",rule.getActivity().getTitle());
    }
}
