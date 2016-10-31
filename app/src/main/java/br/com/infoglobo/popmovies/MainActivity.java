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
    private int currentPage = 1;
    private int totalPages = 0;
    private static final String TAG = "PopMovies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = (RecyclerView) findViewById(R.id.movies_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(linearLayoutManager);

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

        moviesList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) moviesList.getLayoutManager();

                if(moviesAdapter.getItemCount() == layoutManager.findLastCompletelyVisibleItemPosition()+1){

                    if(currentPage < totalPages){
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                        requestMovies();
                    }

                }
            }
        });

        movieService = new MovieService(this);

        requestMovies();

    }

    private void requestMovies(){

        AsyncTask<?,?,PageMoviesDTO> requestTask = new AsyncTask<Object,Object,PageMoviesDTO>(){

            @Override
            protected PageMoviesDTO doInBackground(Object[] params) {

                try{

                    return movieService.getPopularMovies(currentPage+1);

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
                currentPage = dto.getPage();
                totalPages = dto.getTotalPages();
            }
        };

        requestTask.execute();
    }
}
