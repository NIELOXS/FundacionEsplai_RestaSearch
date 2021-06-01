package com.cursfundacionesplai.restasearch.models;

import java.util.ArrayList;

public class OpeningHour {
    private boolean open_now;
    private ArrayList<String> weekday_text;

    public OpeningHour(boolean open_now, ArrayList<String> weekday_text) {
        this.open_now = open_now;
        this.weekday_text = weekday_text;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
    }

    public ArrayList<String> getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(ArrayList<String> weekday_text) {
        this.weekday_text = weekday_text;
    }
}
