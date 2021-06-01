package com.cursfundacionesplai.restasearch.models;

public class Viewport {
    private Possition northeast;
    private Possition southwest;

    public Viewport(Possition northeast, Possition southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public Possition getNortheast() {
        return northeast;
    }

    public void setNortheast(Possition northeast) {
        this.northeast = northeast;
    }

    public Possition getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Possition southwest) {
        this.southwest = southwest;
    }
}
