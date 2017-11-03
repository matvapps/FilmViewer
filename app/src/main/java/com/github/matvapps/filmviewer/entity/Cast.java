package com.github.matvapps.filmviewer.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast {

    public Cast() {
    }

    public Cast(int cast_id,
                String character, String credit_id,
                String gender, String name, int order,
                String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.gender = gender;
        this.name = name;
        this.order = order;
        this.profile_path = profile_path;
    }

    private transient long id;

    private int filmId;

    @Expose
    @SerializedName("cast_id")
    private int cast_id;

    @Expose
    @SerializedName("character")
    private String character;

    @Expose
    @SerializedName("credit_id")
    private String credit_id;

    @Expose
    @SerializedName("gender")
    private String gender;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("order")
    private int order;

    @Expose
    @SerializedName("profile_path")
    private String profile_path;

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
