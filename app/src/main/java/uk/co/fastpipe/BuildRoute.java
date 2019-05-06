package uk.co.fastpipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import uk.co.fastpipe.adapters.StationArrayAdapter;
import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;
import uk.co.fastpipe.models.TubeGraph;
import uk.co.fastpipe.models.TubeReader;

import java.io.IOException;
import java.util.ArrayList;

public class BuildRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_route);
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
        try {
            TubeGraph tube = TubeReader.load(this);
            Graph g = tube.generateGraph();

            final ArrayList<Node> list = new ArrayList<>();

            //final LinearLayout listView2 = findViewById(R.id.linearLayout);
            final ListView listView = findViewById(R.id.routeView);
            final ArrayAdapter<Node> adapter = new StationArrayAdapter(this, list);

            // find stations by their IDs
            for (int i = 0; i < ids.length; i++) {
                // convert each item to number (ID)
                int id = Integer.parseInt(ids[i]);

                // take station names and add them to the list
                Node n = g.getNodeById(id);
                list.add(n);
            }


            // display the list
            listView.setAdapter(adapter);


        } catch (IOException ex) {

        }
    }

}

