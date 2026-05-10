package cz.uhk.timetable.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import cz.uhk.timetable.model.Building;
import cz.uhk.timetable.model.Room;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class StagLocationProvider {
    private static final String BUDOVY_URL = "https://stag-demo.uhk.cz/ws/services/rest2/mistnost/getBudovy?outputFormat=JSON";
    private static final String MISTNOSTI_URL = "https://stag-demo.uhk.cz/ws/services/rest2/mistnost/getMistnostiInfo?zkrBudovy=%s&outputFormat=JSON";
    private final Gson gson = new Gson();

    public List<Building> readBuildings() {
        try {
            var reader = new InputStreamReader(new URL(BUDOVY_URL).openStream());
            var root = gson.fromJson(reader, JsonObject.class);
            return gson.fromJson(root.get("items"),
                    new TypeToken<List<Building>>(){}.getType());
        } catch (Exception e) {
            throw new RuntimeException("Chyba při načítání budov", e);
        }
    }

    public List<Room> readRooms(String building) {
        try {
            var url = new URL(MISTNOSTI_URL.formatted(building));
            var reader = new InputStreamReader(url.openStream());
            var root = gson.fromJson(reader, JsonObject.class);
            return gson.fromJson(root.get("mistnostInfo"),
                    new TypeToken<List<Room>>(){}.getType());
        } catch (Exception e) {
            throw new RuntimeException("Chyba při načítání místností", e);
        }
    }
}