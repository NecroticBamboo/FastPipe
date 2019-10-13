package uk.co.fastpipe.models;

import com.opencsv.bean.CsvBindByName;

public final class StationMask {
    @CsvBindByName private String stations;
    @CsvBindByName private int w;
    @CsvBindByName private int h;
    @CsvBindByName private int x;
    @CsvBindByName private int y;

    public StationMask(String stations,
                       int w,
                       int h,
                       int x,
                       int y) {
        this.stations =stations;
        this.w=w;
        this.h=h;
        this.x=x;
        this.y=y;
    }
    public StationMask() {

    }

    public String getStations() {
        return stations;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
