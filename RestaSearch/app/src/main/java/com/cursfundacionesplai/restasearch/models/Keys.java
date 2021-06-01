package com.cursfundacionesplai.restasearch.models;

public abstract class Keys {

    // SHARED PREFERENCES
    public final static String SHARED_PREFS_NAME = "prefs";
    public final static String PREFS_SAVED_LANG = "language";
    public final static String PREFS_SAVE_POLICY = "key_shared_prefs_policy";

    // DATABASE
    public final static String DATABASE_NAME = "restaurants";
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_LIMIT_HISTORIC = "15";

    //URLS
    public final static String URL_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    public final static String URL_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json?";
    public final static String URL_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?";
    public final static String API_KEY = "";

    public final static String STATUS_CODE_OK = "OK";
    public final static String STATUS_CODE_UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public final static String STATUS_CODE_ZERO_RESULTS = "ZERO_RESULTS";
    public final static String STATUS_CODE_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public final static String STATUS_CODE_REQUEST_DENIED = "REQUEST_DENIED";
    public final static String STATUS_CODE_INVALID_REQUEST = "INVALID_REQUEST";
    public final static String STATUS_CODE_NOT_FOUND = "NOT_FOUND";
}
