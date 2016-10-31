package br.com.infoglobo.popmovies.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.infoglobo.popmovies.model.Movie;

/**
 * Created by rodrigo on 30/10/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageMoviesDTO {
    private int page;
    private List<Movie> results;
    @JsonProperty("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
