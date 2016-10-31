package br.com.infoglobo.popmovies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by rodrigo on 27/10/16.
 */

@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private long id;
    @JsonProperty("original_title")
    private String name;
    @JsonProperty("poster_path")
    private String imageUrl;
    @JsonProperty("vote_average")
    private float rating;
    private String overview;
    private int runtime;
    private List<Genre> genres;
    @JsonProperty("original_language")
    private String language;

    public Movie(){}

    public Movie(String name,float rating){
        setName(name);
        setRating(rating);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
