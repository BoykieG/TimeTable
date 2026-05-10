package cz.uhk.timetable.model;

import com.google.gson.annotations.SerializedName;

public class Building {
    @SerializedName("zkrBudovy")
    private String id;

    public String getId() { return id; }

    @Override
    public String toString() { return id; }
}