package br.com.infoglobo.popmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Iterator;

import br.com.infoglobo.popmovies.model.Genre;
import br.com.infoglobo.popmovies.model.Movie;
import br.com.infoglobo.popmovies.service.MovieService;


public class DetailsActivity extends AppCompatActivity {

    private Movie movie;
    private ProgressBar progressBar;
    private ImageView movieImage;
    private TextView movieNameText;
    private TextView movieGenresText;
    private TextView movieRuntimeText;
    private TextView movieLanguageText;
    private TextView movieDetailsText;
    private RatingBar movieRatingBar;
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        progressBar = (ProgressBar) findViewById(R.id.progressBar_movie_details);
        movieImage = (ImageView) findViewById(R.id.movie_details_image);
        movieNameText = (TextView) findViewById(R.id.movie_details_name);
        movieGenresText = (TextView) findViewById(R.id.movie_details_genres);
        movieRuntimeText = (TextView) findViewById(R.id.movie_details_runtime);
        movieLanguageText = (TextView) findViewById(R.id.movie_details_language);
        movieDetailsText = (TextView) findViewById(R.id.movie_details_overview);
        movieRatingBar = (RatingBar) findViewById(R.id.movie_details_rating);

        AsyncTask<?,?,Movie> requestTask = new AsyncTask<Object, Object, Movie>() {

            @Override
            protected Movie doInBackground(Object... params) {

                MovieService movieService = new MovieService(DetailsActivity.this);

                return movieService.getMovieDetails(movie.getId());
            }

            @Override
            protected void onPostExecute(Movie movie) {

                progressBar.setVisibility(ProgressBar.INVISIBLE);

                if(movie == null){
                    Log.e(TAG,"error request movie details");
                    Toast.makeText(DetailsActivity.this,getString(R.string.get_movie_error_details),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(TAG,"movie details id "+movie.getId());
                populateViews(movie);
            }
        };

        requestTask.execute();

    }

    private void populateViews(Movie movie){

        movieNameText.setText(movie.getName());
        movieRuntimeText.setText(movie.getRuntime()+" min");
        movieLanguageText.setText(movie.getLanguage());
        movieRatingBar.setRating(movie.getRating());
        movieDetailsText.setText(movie.getOverview());
        Picasso.with(this)
                .load(String.format("%s%s",getString(R.string.image_cdn),movie.getImageUrl()))
                .resize(250,350)
                .placeholder(R.drawable.no_image)
                .into(movieImage);

        StringBuffer genresText = new StringBuffer();

        Iterator<Genre> genres = movie.getGenres().iterator();

        while(genres.hasNext()){
            Genre genre = genres.next();

            if(genres.hasNext())
                genresText.append(String.format("%s\n",genre.getName()));
            else
                genresText.append(genre.getName());
        }


        movieGenresText.setText(genresText.toString());

        movieNameText.setVisibility(View.VISIBLE);
        movieRatingBar.setVisibility(View.VISIBLE);
    }

}
