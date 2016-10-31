package br.com.infoglobo.popmovies.service;

import android.content.Context;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import br.com.infoglobo.popmovies.R;
import br.com.infoglobo.popmovies.dto.PageMoviesDTO;
import br.com.infoglobo.popmovies.model.Movie;

/**
 * Created by rodrigo on 30/10/16.
 */

public class MovieService {

    private RestTemplate template;
    private Context context;
    private String baseUrl;

    public MovieService(Context context){
        this.template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        this.context = context;
        this.baseUrl = context.getString(R.string.api_url);
    }

    public PageMoviesDTO getPopularMovies(){
        String endpoint = getEndpoint(R.string.popular_endpoint);

        ResponseEntity<PageMoviesDTO> response = template.getForEntity(endpoint, PageMoviesDTO.class);
        if(response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            return null;
    }

    public Movie getMovieDetails(long movieId){

        String endpoint = getEndpointWithId(R.string.movie_endpoint,movieId);
        ResponseEntity<Movie> response = template.getForEntity(endpoint,Movie.class);

        if(response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            return null;
    }

    private String getEndpointWithId(int endpointResId, long movieId){

        return getEndpoint(endpointResId).replace("{id}",String.valueOf(movieId));
    }

    private String getEndpoint(int endpointResId){

        return String.format("%s%s?api_key=%s",baseUrl,context.getString(endpointResId),
                context.getString(R.string.api_token));
    }
}
