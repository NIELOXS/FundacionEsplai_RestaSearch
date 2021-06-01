package com.cursfundacionesplai.restasearch.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Review {
    private String author_name;
    private String author_url;
    private String language;
    private String profile_photo_url;
    private double rating;
    private String relative_time_description;
    private String text;

    public Review(String author_name, String author_url, String language, String profile_photo_url, double rating, String relative_time_description, String text) {
        this.author_name = author_name;
        this.author_url = author_url;
        this.language = language;
        this.profile_photo_url = profile_photo_url;
        this.rating = rating;
        this.relative_time_description = relative_time_description;
        this.text = text;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getRelative_time_description() {
        return relative_time_description;
    }

    public void setRelative_time_description(String relative_time_description) {
        this.relative_time_description = relative_time_description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
