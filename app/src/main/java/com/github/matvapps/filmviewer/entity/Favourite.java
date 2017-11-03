package com.github.matvapps.filmviewer.entity;

import com.orm.SugarRecord;

/**
 * Created by Alexandr on 02/11/2017.
 */

public class Favourite extends SugarRecord {

    public Favourite() {
    }

    private int favouriteMovieId;

    public Favourite(int favouriteMovieId) {
        this.favouriteMovieId = favouriteMovieId;
    }

    public int getFavouriteMovieId() {
        return favouriteMovieId;
    }

    public void setFavouriteMovieId(int favouriteMovieId) {
        this.favouriteMovieId = favouriteMovieId;
    }
}
