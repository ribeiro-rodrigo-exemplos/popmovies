package br.com.infoglobo.popmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import br.com.infoglobo.popmovies.R;
import br.com.infoglobo.popmovies.model.Movie;

/**
 * Created by rodrigo on 27/10/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;
    private Context context;
    private LayoutInflater inflater;

    public MoviesAdapter(List<Movie> movies, Context context){
        this.movies = movies;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.card_movie,parent,false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.movieName.setText(movie.getName());
        holder.movieRating.setRating(movie.getRating());
        holder.ratingNumber.setText(context.getString(R.string.movie_rating_list)+" "+
                new DecimalFormat("#.##").format(movie.getRating()));

        Picasso.with(context)
                .load(String.format("%s%s",context.getString(R.string.image_cdn),movie.getImageUrl()))
                .placeholder(R.drawable.no_image)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Movie getItemAtPosition(int position){
        return movies.get(position);
    }

    public void addMovies(List<Movie> newMovies){
        int size = this.movies.size();
        this.movies.addAll(newMovies);
        this.notifyItemRangeInserted(size,movies.size());
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder{

        ImageView movieImage;
        RatingBar movieRating;
        TextView  movieName;
        TextView ratingNumber;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            movieImage = (ImageView) itemView.findViewById(R.id.movie_image);
            movieName = (TextView) itemView.findViewById(R.id.movie_name);
            movieRating = (RatingBar) itemView.findViewById(R.id.movie_rating);
            ratingNumber = (TextView) itemView.findViewById(R.id.movie_rating_number);

        }

    }

}
