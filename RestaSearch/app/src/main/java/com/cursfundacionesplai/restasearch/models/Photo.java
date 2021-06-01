package com.cursfundacionesplai.restasearch.models;

import java.util.ArrayList;

public class Photo {
    private int height;
    private ArrayList<String> html_attributions;
    private String photo_reference;
    private int width;

    public Photo(Integer height, ArrayList<String> htmlAttributions, String photoReference, Integer width) {
        this.height = height;
        this.html_attributions = htmlAttributions;
        this.photo_reference = photoReference;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public ArrayList<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(ArrayList<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String buildUrl() {
        return String.format("%sphotoreference=%s&maxheight=%s&maxwidth=%s&key=%s", Keys.URL_PHOTO, photo_reference, height, width, Keys.API_KEY);
    }
}
