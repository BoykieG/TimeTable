package cz.uhk.timetable.model;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("cisloMistnosti")
    private String number;
    @SerializedName("zkrBudovy")
    private String building;

    public String getNumber() { return number; }
    public String getBuilding() { return building; }

    @Override
    public String toString() { return number; }
}