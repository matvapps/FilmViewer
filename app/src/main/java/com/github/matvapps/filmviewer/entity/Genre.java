package com.github.matvapps.filmviewer.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Genre extends SugarRecord{

    public Genre() {
    }

    public Genre(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    private transient long id;

    @Expose
    @SerializedName("id")
    private int genreId;

    @Expose
    @SerializedName("name")
    private String genreName;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
