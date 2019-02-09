package uk.co.fastpipe.models;

import android.content.Context;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import uk.co.fastpipe.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class TubeReader {
    public static TubeGraph load(Context context) throws IOException {

        TubeStation[] stationsArray;
        TubeConnection[] connectionsArray;
        TubeLine[] linesArray;

        stationsArray = readTubeStations(context);
        connectionsArray = readTubeConnections(context);
        //linesArray = readTubeLines(context);
        linesArray = null;
        //TODO read lines

        TubeGraph graph = new TubeGraph(stationsArray,connectionsArray,linesArray);
        return graph;
    }
    private static TubeStation[] readTubeStations(Context context) throws IOException {
        TubeStation[] stationsArray;
        try (Reader stationsReader= new InputStreamReader(context.getResources().openRawResource(R.raw.london_stations)) ) {
            CsvToBean<TubeStation> csvToBean = new CsvToBeanBuilder(stationsReader)
                    .withType(TubeStation.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            List<TubeStation> stationsList = csvToBean.parse();

            stationsArray = stationsList.toArray(new TubeStation[stationsList.size()]);
        }
        return stationsArray;
    }
    private static TubeConnection[] readTubeConnections(Context context) throws IOException {
        TubeConnection[] array;
        try (Reader reader= new InputStreamReader(context.getResources().openRawResource(R.raw.london_connections)) ) {
            CsvToBean<TubeConnection> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TubeConnection.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            List<TubeConnection> data = csvToBean.parse();

            array = data.toArray(new TubeConnection[data.size()]);
        }
        return array;
    }
    private static TubeLine[] readTubeLines(Context context) throws IOException {
        TubeLine[] array;
        try (Reader reader= new InputStreamReader(context.getResources().openRawResource(R.raw.london_lines)) ) {
            CsvToBean<TubeLine> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TubeLine.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            List<TubeLine> data = csvToBean.parse();

            array = data.toArray(new TubeLine[data.size()]);
        }
        return array;
    }
}
