package cz.uhk.timetable.gui;

import cz.uhk.timetable.model.Activity;
import cz.uhk.timetable.model.Building;
import cz.uhk.timetable.model.LocationTimetable;
import cz.uhk.timetable.model.Room;
import cz.uhk.timetable.utils.ITimetableProvider;
import cz.uhk.timetable.utils.StagLocationProvider;
import cz.uhk.timetable.utils.StagTimetableProvider;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class TimetableFrame extends JFrame {
    private ITimetableProvider timetableProvider = new StagTimetableProvider();
    private LocationTimetable timetable;
    private JTable tabTimetable;
    private TimetableModel timetableModel;
    private StagLocationProvider locationProvider = new StagLocationProvider();
    private JComboBox<Building> combBuilding;
    private JComboBox<Room> combRoom;
    public TimetableFrame() {
        super("Location Timetable");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        timetable = new LocationTimetable();
        initGui();
    }

    private void initGui() {
        JPanel topPanel = new JPanel();

        combBuilding = new JComboBox<>();
        combRoom = new JComboBox<>();
        JButton btnLoad = new JButton("Načíst");

        locationProvider.readBuildings().forEach(combBuilding::addItem);

        combBuilding.addActionListener(e -> {
            combRoom.removeAllItems();
            Building selected = (Building) combBuilding.getSelectedItem();
            if (selected != null) {
                locationProvider.readRooms(selected.getId()).forEach(combRoom::addItem);
            }
        });

        combBuilding.getActionListeners()[0].actionPerformed(null);

        btnLoad.addActionListener(e -> {
            Building building = (Building) combBuilding.getSelectedItem();
            Room room = (Room) combRoom.getSelectedItem();
            if (building != null && room != null) {
                timetable = timetableProvider.readTimetable(building.getId(), room.getNumber());
                timetableModel.fireTableDataChanged();
                setTitle("Rozvrh místnosti " + timetable.getBuilding() + " - " + timetable.getRoom());
            }
        });

        topPanel.add(new JLabel("Budova:"));
        topPanel.add(combBuilding);
        topPanel.add(new JLabel("Místnost:"));
        topPanel.add(combRoom);
        topPanel.add(btnLoad);
        add(topPanel, BorderLayout.NORTH);

        timetableModel = new TimetableModel();
        tabTimetable = new JTable(timetableModel);
        add(new JScrollPane(tabTimetable), BorderLayout.CENTER);

        pack();
    }


    class TimetableModel extends AbstractTableModel {

        private static final String[] COLNAMES = {
                "ZKRATKA",
                "NÁZEV",
                "UČITEL",
                "TYP",
                "DEN",
                "ZAČÁTEK",
                "KONEC"
        };

        private List<Activity> getFilteredActivities() {
            return timetable.getActivities().stream()
                    .filter(a -> a.getType().equals("Přednáška") || a.getType().equals("Cvičení"))
                    .toList();
        }
        @Override
        public int getRowCount() {
            return getFilteredActivities().size();
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int column) {
            return COLNAMES[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var activities = getFilteredActivities();
            var a = activities.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> a.getId();
                case 1 -> a.getName();
                case 2 -> a.getTeacher();
                case 3 -> a.getType();
                case 4 -> a.getDay();
                case 5 -> a.getStart();
                case 6 -> a.getEnd();
                default -> "";
            };
        }
    }
}
