package com.cursfundacionesplai.restasearch.models;

import java.util.ArrayList;

public class AddressComponent {
    private String long_name;
    private String shot_name;
    private ArrayList<String> types;

    public AddressComponent(String long_name, String shot_name, ArrayList<String> types) {
        this.long_name = long_name;
        this.shot_name = shot_name;
        this.types = types;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShot_name() {
        return shot_name;
    }

    public void setShot_name(String shot_name) {
        this.shot_name = shot_name;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }
}
