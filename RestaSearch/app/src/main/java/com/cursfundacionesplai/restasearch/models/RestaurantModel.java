package com.cursfundacionesplai.restasearch.models;

import java.util.ArrayList;

public class RestaurantModel {
    private ArrayList<AddressComponent> address_components;
    private String adr_address;
    private String business_status;
    private String formatted_address;
    private String formatted_phone_number;
    private Geometry geometry;
    private String icon;
    private String international_phone_number;
    private String name;
    private OpeningHour opening_hours;
    private ArrayList<Photo> photos;
    private String place_id;
    private PlusCode plus_code;
    private int price_level;
    private double rating;
    private String reference;
    private ArrayList<Review> reviews;
    private ArrayList<String> types;
    private String url;
    private int user_ratings_total;
    private int utc_offset;
    private String vicinity;
    private String website;

    public RestaurantModel() {
    }

    public RestaurantModel(ArrayList<AddressComponent> address_components, String adr_address, String business_status, String formatted_address, String formatted_phone_number, Geometry geometry, String icon, String international_phone_number, String name, OpeningHour opening_hours, ArrayList<Photo> photos, String place_id, PlusCode plus_code, int price_level, double rating, String reference, ArrayList<Review> reviews, ArrayList<String> types, String url, int user_ratings_total, int utc_offset, String vicinity, String website) {
        this.address_components = address_components;
        this.adr_address = adr_address;
        this.business_status = business_status;
        this.formatted_address = formatted_address;
        this.formatted_phone_number = formatted_phone_number;
        this.geometry = geometry;
        this.icon = icon;
        this.international_phone_number = international_phone_number;
        this.name = name;
        this.opening_hours = opening_hours;
        this.photos = photos;
        this.place_id = place_id;
        this.plus_code = plus_code;
        this.price_level = price_level;
        this.rating = rating;
        this.reference = reference;
        this.reviews = reviews;
        this.types = types;
        this.url = url;
        this.user_ratings_total = user_ratings_total;
        this.utc_offset = utc_offset;
        this.vicinity = vicinity;
        this.website = website;
    }

    public ArrayList<AddressComponent> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(ArrayList<AddressComponent> address_components) {
        this.address_components = address_components;
    }

    public String getAdr_address() {
        return adr_address;
    }

    public void setAdr_address(String adr_address) {
        this.adr_address = adr_address;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
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

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
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

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
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

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public int getUtc_offset() {
        return utc_offset;
    }

    public void setUtc_offset(int utc_offset) {
        this.utc_offset = utc_offset;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
