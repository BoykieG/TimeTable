package cz.uhk.timetable;

import cz.uhk.timetable.gui.TimetableFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimetableFrame().setVisible(true));
    }
}
