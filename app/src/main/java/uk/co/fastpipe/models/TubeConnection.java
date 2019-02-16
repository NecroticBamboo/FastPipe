package uk.co.fastpipe.models;
import com.opencsv.bean.CsvBindByName;

public final class TubeConnection {
    @CsvBindByName private int station1;
    @CsvBindByName private int station2;
    @CsvBindByName private int line;
    @CsvBindByName private int time;

    public TubeConnection(
            int station1,
            int station2,
            int line,
            int time) {
        this.station1 = station1;//need this for node
        this.station2 = station2;//need this for node
        this.line = line;
        this.time = time;
    }
    public TubeConnection() {

    }

    public final int getStation1() {
        return station1;
    }

    public final int getStation2() {
        return station2;
    }

    public final int getLine() {
        return line;
    }

    public final int getTime() {
        return time;
    }

}