package com.cursfundacionesplai.restasearch.models;

public class Geometry {

    private Possition location;
    private Viewport viewport;

    public Geometry(Possition location, Viewport viewport) {
        this.location = location;
        this.viewport = viewport;
    }

    public Possition getLocation() {
        return location;
    }

    public void setLocation(Possition location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
