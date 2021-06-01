package com.cursfundacionesplai.restasearch.models;

import java.util.ArrayList;

public class RestaurantList {
    private String business_status;
    private Geometry geometry;
    private String icon;
    private String name;
    private OpeningHour opening_hours;
    private ArrayList<Photo> photos;
    private String place_id;
    private PlusCode plus_code;
    private int price_level;
    private double rating;
    private String reference;
    private String scope;
    private ArrayList<String> types;
    private int user_ratings_total;
    private String vicinity;
    private String formattedAdress;
    private String internationalPhoneNumber;

    public String getFormattedAdress() {
        return formattedAdress;
    }

    public void setFormattedAdress(String formattedAdress) {
        this.formattedAdress = formattedAdress;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public RestaurantList() {
    }

    public RestaurantList(String business_status,
                          Geometry geometry,
                          String icon,
                          String name,
                          OpeningHour openingHour,
                          ArrayList<Photo> photos,
                          String placesId,
                          PlusCode plusCode,
                          int priceLevel,
                          double rating,
                          String reference,
                          String scope,
                          ArrayList<String> types,
                          int userRatingsTotal,
                          String vicinity) {
        this.business_status = business_status;
        this.geometry = geometry;
        this.icon = icon;
        this.name = name;
        this.opening_hours = openingHour;
        this.photos = photos;
        this.place_id = placesId;
        this.plus_code = plusCode;
        this.price_level = priceLevel;
        this.rating = rating;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.user_ratings_total = userRatingsTotal;
        this.vicinity = vicinity;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHour getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHour opening_hours) {
        this.opening_hours = opening_hours;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String places_id) {
        this.place_id = places_id;
    }

    public PlusCode getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(PlusCode plus_code) {
        this.plus_code = plus_code;
    }

    public int getPrice_level() {
        return price_level;
    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
