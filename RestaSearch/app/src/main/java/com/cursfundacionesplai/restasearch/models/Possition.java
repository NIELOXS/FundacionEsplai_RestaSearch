package com.cursfundacionesplai.restasearch.models;

public class Possition {
    private float lat;
    private float lng;

    public Possition(float latitude, float longitude) {
        this.lat = latitude;
        this.lng = longitude;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
