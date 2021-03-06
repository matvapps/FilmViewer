package com.github.matvapps.filmviewer;

import com.google.gson.annotations.SerializedName;

public class CastEntity {

    @SerializedName("cast_id")
    private int cast_id;

    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("gender")
    private String gender;

    @SerializedName("name")
    private String name;

    @SerializedName("order")
    private int order;

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
