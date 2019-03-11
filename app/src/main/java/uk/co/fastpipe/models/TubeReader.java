package uk.co.fastpipe.models;

import android.content.Context;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import uk.co.fastpipe.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class TubeReader {
    public static TubeGraph load(Context context) throws IOException {

        TubeStation[] stationsArray;
        TubeConnection[] connectionsArray;
        TubeLine[] linesArray;

        stationsArray = readTubeStations(context.getResources().openRawResource(R.raw.london_stations));
        connectionsArray = readTubeConnections(context.getResources().openRawResource(R.raw.london_connections));
        //linesArray = readTubeLines(context.getResources().openRawResource(R.raw.london_lines));
        linesArray = null;    //TODO read lines

        TubeGraph graph = new TubeGraph(stationsArray,connectionsArray,linesArray);
        return graph;
    }

    public static TubeGraph load(InputStream stations, InputStream connections, InputStream lines) throws IOException {

        TubeStation[] stationsArray;
        TubeConnection[] connectionsArray;
        TubeLine[] linesArray;

        stationsArray = readTubeStations(stations);
        connectionsArray = readTubeConnections(connections);
        //linesArray = readTubeLines(lines);
        linesArray = null; //TODO read lines

        TubeGraph graph = new TubeGraph(stationsArray,connectionsArray,linesArray);
        return graph;
    }

    private static TubeStation[] readTubeStations(InputStream data) throws IOException {
        TubeStation[] stationsArray;
        try (Reader stationsReader= new InputStreamReader( data ) ) {
            CsvToBean<TubeStation> csvToBean = new CsvToBeanBuilder(stationsReader)
                    .withType(TubeStation.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<TubeStation> stationsList = csvToBean.parse();

            stationsArray = stationsList.toArray(new TubeStation[stationsList.size()]);
        }
        return stationsArray;
    }

    private static TubeConnection[] readTubeConnections(InputStream stream) throws IOException {
        TubeConnection[] array;
        try (Reader reader= new InputStreamReader(stream) ) {
            CsvToBean<TubeConnection> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TubeConnection.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            List<TubeConnection> data = csvToBean.parse();

            array = data.toArray(new TubeConnection[data.size()]);
        }
        return array;
    }
    private static TubeLine[] readTubeLines(InputStream stream) throws IOException {
        TubeLine[] array;
        try (Reader reader= new InputStreamReader(stream) ) {
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
