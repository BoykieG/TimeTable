package cz.uhk.timetable.utils;

import cz.uhk.timetable.model.Activity;
import cz.uhk.timetable.model.LocationTimetable;

import java.time.LocalTime;

public class MockTimetableProvider implements ITimetableProvider {
    @Override
    public LocationTimetable readTimetable(String building, String room) {
        var tt = new LocationTimetable("J", "J22");
        tt.getActivities().add(new Activity(
                "ZMI2", "Základy matematiky pro informatiky II", "Bauer",
                "Pondělí", "Přednáška",
                LocalTime.of(9,55),
                LocalTime.of(11,30)
        ));
        tt.getActivities().add(new Activity(
                "PRO1", "Programovani I", "Kozel",
                "Pondělí", "Cvičení",
                LocalTime.of(11,35),
                LocalTime.of(13,10)
        ));
        tt.getActivities().add(new Activity(
                "OS1A", "Operační systémy I", "Horálek",
                "Pondělí", "Přednáška",
                LocalTime.of(13,15),
                LocalTime.of(14,50)
        ));

        return tt;
    }
}
