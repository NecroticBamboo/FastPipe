package uk.co.fastpipe.models;

import com.opencsv.bean.CsvBindByName;

public final class TubeStation {
    @CsvBindByName
    private int id = 0;
    @CsvBindByName
    private double latitude = 0;
    @CsvBindByName
    private double longitude = 0;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String display_name;
    @CsvBindByName
    private double zone = 0;
    @CsvBindByName
    private int total_lines = 0;
    @CsvBindByName
    private boolean rail = false;

    public TubeStation() {

    }

    public TubeStation(
            int id,
            double latitude,
            double longitude,
            String name,
            String display_name,
            double zone,
            int total_lines,
            boolean rail) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.display_name = display_name;
        this.zone = zone;
        this.total_lines = total_lines;
        this.rail = rail;
    }

    public final int getId() {
        return id;
    }

    public final double getLatitude() {
        return latitude;
    }

    public final double getLongitude() {
        return longitude;
    }

    public final String getName() {
        return name;
    }

    public final String getDisplayName() {
        return display_name;
    }

    public final double getZone() {
        return zone;
    }

    public final int getTotalLines() {
        return total_lines;
    }

    public final boolean getRail() {
        return rail;
    }
}
