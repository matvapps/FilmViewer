package com.github.matvapps.filmviewer.entity;

import android.graphics.Bitmap;

import com.github.matvapps.filmviewer.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

public class Movie extends SugarRecord implements Serializable {

    public Movie() {}

    public Movie(int vote_count, int movie_id, boolean video, float vote_average,
                 String title, float popularity, String poster_path, String original_language,
                 int[] genre_ids, String backdrop_path, boolean adult,String overview,
                 String release_date, String genresString) {

        this.vote_count = vote_count;
        this.movie_id = movie_id;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.genre_ids = genre_ids;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genresString = genresString;

    }

    protected byte[] image;

    @Expose
    @SerializedName("vote_count")
    private int vote_count;

    @Expose
    @SerializedName("id")
    @Unique
    private int movie_id;

    @Expose
    @SerializedName("video")
    private boolean video;

    @Expose
    @SerializedName("vote_average")
    private float vote_average;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("popularity")
    private float popularity;

    @Expose
    @SerializedName("poster_path")
    private String poster_path;

    @Expose
    @SerializedName("original_language")
    private String original_language;

    @Expose
    @SerializedName("genre_ids")
    private int[] genre_ids;

    @Expose
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @Expose
    @SerializedName("adult")
    private boolean adult;

    @Expose
    @SerializedName("overview")
    private String overview;

    @Expose
    @SerializedName("release_date")
    private String release_date;

    private String genresString;

    public String getGenresString() {
        return genresString;
    }

    public void setGenresString(String genresString) {
        this.genresString = genresString;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Bitmap getImage() {
        return Utility.convertByteArrayToBitmap(image);
    }

    public void setImage(Bitmap bitmap) {
        this.image = Utility.GetByteFromBitmap(bitmap);
    }

}

