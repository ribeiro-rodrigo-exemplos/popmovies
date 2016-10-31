package br.com.infoglobo.popmovies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by rodrigo on 30/10/16.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {

    private long id;
    private String name;

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
}
