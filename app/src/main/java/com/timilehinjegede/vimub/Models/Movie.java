package com.timilehinjegede.vimub.Models;

import com.timilehinjegede.vimub.Volley.URLs;

public class Movie {

    private int id ;
    private String title ;
    private String release_date ;
    private String poster ;
    private float rating ;
    private String overview ;
    private String backdrop ;


    public Movie(int id, String title, String release_date, String poster, float rating,String overview,String backdrop) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.poster = URLs.POSTER_BASE_URL+ poster;
        this.rating = rating ;
        this.overview = overview ;
        this.backdrop = URLs.BACKDROP_BASE_URL+ backdrop ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
