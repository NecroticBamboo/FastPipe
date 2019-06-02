package uk.co.fastpipe.models;
import android.graphics.Color;
import com.opencsv.bean.CsvBindByName;

public final class TubeLine {
    @CsvBindByName private int line = 0;
    @CsvBindByName private String name;
    @CsvBindByName private  String colour;
    @CsvBindByName private String strip;

    public TubeLine(int line,
                    String name,
                    String colour) {
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

    public  final int getColor() {
        Integer r = Integer.parseInt(colour.substring(0,2), 16);
        Integer g = Integer.parseInt(colour.substring(2,4), 16);
        Integer b = Integer.parseInt(colour.substring(4,6), 16);
        return Color.rgb(r,g,b);
//        switch (colour){
//            case "AE6017":
//                return Color.rgb(68,38,9);
//
//            case "FFE02B":
//                return Color.rgb(100,88,17);
//
//            case "F491A8":
//                return Color.rgb(244,145,168);
//
//            case "949699":
//                return Color.rgb(148,150,153);
//
//            case "0A9CDA":
//                return Color.rgb(10,156,218);
//
//            case "F15B2E":
//                return Color.rgb(241,91,46);
//
//            case "00A166":
//                return Color.rgb(0,63,40);
//
//            case "FBAE34":
//                return Color.rgb(251,174,52);
//
//            case "91005A":
//                return Color.rgb(145,0,90);
//
//            case "000000":
//                return Color.rgb(0,0,0);
//
//            case "094FA3":
//                return Color.rgb(9,79,163);
//
//            case "88D0C4":
//                return Color.rgb(136,208,196);
//
//            case "00A77E":
//                return Color.rgb(0,65,49);
//
//            default:
//                return Color.rgb(255,255,255);
//        }
    }

    public final String getStrip() {
        return strip;
    }
}
