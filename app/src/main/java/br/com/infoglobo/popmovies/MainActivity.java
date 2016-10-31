package br.com.infoglobo.popmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import br.com.infoglobo.popmovies.adapter.MoviesAdapter;
import br.com.infoglobo.popmovies.dto.PageMoviesDTO;
import br.com.infoglobo.popmovies.model.Movie;
import br.com.infoglobo.popmovies.service.MovieService;
import br.com.infoglobo.popmovies.util.RecyclerViewListener;
import br.com.infoglobo.popmovies.util.RecyclerViewTouchListener;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private MoviesAdapter moviesAdapter;
    private MovieService movieService;
    private ProgressBar progressBar;
    private static final String TAG = "PopMovies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = (RecyclerView) findViewById(R.id.movies_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(linearLayoutManager);

        /*List<Movie> movies = Arrays.asList(
                new Movie("X-Men",10f),
                new Movie("Salt",8f),
                new Movie("Super Girl",9f),
                new Movie("Lost",10f),
                new Movie("Salt",4.5f),
                new Movie("Super Girl",9.2f),
                new Movie("Lost",7.3f));

        final MoviesAdapter adapter = new MoviesAdapter(movies,this);
        moviesList.setAdapter(adapter); */

        moviesAdapter = new MoviesAdapter(new ArrayList<Movie>(),MainActivity.this);
        moviesList.setAdapter(moviesAdapter);

        moviesList.addOnItemTouchListener(new RecyclerViewTouchListener(this, moviesList,
                new RecyclerViewListener() {
            @Override
            public void onClickListener(View view, MotionEvent event, int position) {

                MoviesAdapter adapter = (MoviesAdapter) moviesList.getAdapter();

                Movie selectedMovie = adapter.getItemAtPosition(position);

                Intent detailsIntent = new Intent(MainActivity.this,DetailsActivity.class);
                detailsIntent.putExtra("movie", Parcels.wrap(selectedMovie));
                startActivity(detailsIntent);
            }
        }));


        movieService = new MovieService(this);

        AsyncTask<?,?,PageMoviesDTO> requestTask = new AsyncTask<Object,Object,PageMoviesDTO>(){

            @Override
            protected PageMoviesDTO doInBackground(Object[] params) {

                try{

                    return movieService.getPopularMovies();

                }catch (Exception e){
                    Log.e(TAG,e.getMessage(),e);
                    return null;
                }

            }

            @Override
            protected void onPostExecute(PageMoviesDTO dto) {

                if(dto == null){
                    Log.e(TAG,"request movie list error");
                    Toast.makeText(MainActivity.this,getString(R.string.get_movies_error_request),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(TAG,"page: "+dto.getPage()+" total: "+dto.getTotalPages());

                moviesAdapter.addMovies(dto.getResults());
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        };

        requestTask.execute();

    }
}
