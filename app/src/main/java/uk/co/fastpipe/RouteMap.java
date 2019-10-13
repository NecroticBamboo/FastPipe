package uk.co.fastpipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import uk.co.fastpipe.Map.TouchImageView;
import uk.co.fastpipe.adapters.StationArrayAdapter;
import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;
import uk.co.fastpipe.models.MapImageLoader;
import uk.co.fastpipe.models.StationMask;
import uk.co.fastpipe.models.TubeGraph;
import uk.co.fastpipe.models.TubeReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class RouteMap extends AppCompatActivity {

    private TouchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        Bitmap bmpimg = MapImageLoader.loadMapImage(this);
        mImageView = findViewById(R.id.imageMapView);
        mImageView.setImageBitmap(bmpimg);
        mImageView.setMaxZoom(15f);
        try {
            StationMask[] stationsMaskArray = readStationMask(getResources().openRawResource(R.raw.station_masks));
            Intent intent = getIntent();

            String routeStr = intent.getStringExtra(FastPipeActivity.ROUTE_STRING);
            String[] ids;

// Must call equals because Java compares strings by reference
            if (routeStr.equals("")) {
                ids = new String[0];

            } else {
                // split route str by ','
                ids = routeStr.split(",");
            }

            // load tube graph
            TubeGraph tube = TubeReader.load(this);
            Graph g = tube.generateGraph();

            final ArrayList<Node> list = new ArrayList<>();

            //final LinearLayout listView2 = findViewById(R.id.linearLayout);
            final ArrayAdapter<Node> adapter = new StationArrayAdapter(this, list);

            // find stations by their IDs
            for (int i = 0; i < ids.length; i++) {
                // convert each item to number (ID)
                int id = Integer.parseInt(ids[i]);

                // take station names and add them to the list
                Node n = g.getNodeById(id);
                list.add(n);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static StationMask[] readStationMask(InputStream data) throws IOException {
        StationMask[] stationsMaskArray;
        try (Reader stationsReader = new InputStreamReader(data)) {
            CsvToBean<StationMask> csvToBean = new CsvToBeanBuilder(stationsReader)
                    .withType(StationMask.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<StationMask> stationsMaskList = csvToBean.parse();

            stationsMaskArray = stationsMaskList.toArray(new StationMask[stationsMaskList.size()]);
        }
        return stationsMaskArray;
    }

}
