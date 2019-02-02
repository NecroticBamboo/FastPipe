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
        try ( Reader reader = new InputStreamReader(context.getResources().openRawResource(R.raw.london_stations)) ) {
            CsvToBean<TubeStation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TubeStation.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<TubeStation> stations = csvToBean.parse();

            TubeStation[] array = stations.toArray(new TubeStation[stations.size()]);
            TubeGraph graph = new TubeGraph(array);
            return graph;
        }
    }
}
