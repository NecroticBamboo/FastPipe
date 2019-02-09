package uk.co.fastpipe.models;
import com.opencsv.bean.CsvBindByName;

public final class TubeLine {
    @CsvBindByName private int line = 0;
    @CsvBindByName private String name;
    @CsvBindByName private int colour = 0;
    @CsvBindByName private String strip;

    public TubeLine(int line,
                    String name,
                    int colour) {
        this.line = line;
        this.name = name;
        this.colour = colour;
    }
    public TubeLine() {

    }

    public final int getLine() {
        return line;
    }

    public final String getName() {
        return name;
    }

    public final int getColour() {
        return colour;
    }

    public final String getStrip() {
        return strip;
    }
}
