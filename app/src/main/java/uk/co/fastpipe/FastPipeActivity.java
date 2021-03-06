package uk.co.fastpipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import uk.co.fastpipe.Map.MapActivity;
import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;
import uk.co.fastpipe.models.TubeGraph;
import uk.co.fastpipe.models.TubeReader;
import uk.co.fastpipe.models.TubeStation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FastPipeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String ROUTE_STRING = "route_string";

    private TubeGraph tube;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_pipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // this is for future extension. not working yet. was added by the wizard

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // end of future

        // load tube graph and set up spinners
        try {
            tube = TubeReader.load(this);
            TubeStation[] stations = tube.getTubeStations();
            ArrayList<String> spinnerArray = new ArrayList<>();

            // both spinners will contain the same list of stations
            for (int i = 0; i < stations.length; i++) {
                spinnerArray.add(stations[i].getName());
            }

            setUpSinner(spinnerArray, R.id.spinnerFirstStation,  R.id.editTextFirstSation);
            setUpSinner(spinnerArray, R.id.spinnerSecondStation, R.id.editTextSecondStation);

        } catch (IOException ex) {

        }

    }


    /**
     * Set up spinner with data and attach listener. Add "on text changed" reaction
     * @param stationNames - the full list of tube stations
     * @param spinnerId - id of spinner to set up
     * @param editTextId - id of text field to attach to
     */
    private void setUpSinner(ArrayList<String> stationNames, int spinnerId, int editTextId) {
        final Spinner spinner = findViewById(spinnerId); // find the spinner by Id

        // create ArrayAdapter which will link stationNames to spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                stationNames);

        // attach adapter to spinner. From now on spinner will display station names
        spinner.setAdapter(adapter);

        // find editText by id
        final EditText editText2 = findViewById(editTextId);

        // attach TextWatcher to editText. It will react on changes in the input string
        editText2.addTextChangedListener(new TextWatcher() {
            boolean _ignore = false; // indicates if the change was made by the TextWatcher itself.

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (_ignore)
                    return;
                _ignore = true; // prevent infinite loop

                adapter.getFilter().filter(editText2.getText().toString());

                _ignore = false; // release, so the TextWatcher start to listen again.
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // does nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // does nothing
            }
        });

    }



    /**
     * Join stationID as String
     *
     * @param list
     * @param conjunction
     * @return
     */
    static private String join(List<Node> list, String conjunction)  //TODO write test in FastPipeActivityTest
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Node item : list) {
            if (first)
                first = false;
            else
                sb.append(conjunction);
            sb.append(item.getStation().getId());
        }
        return sb.toString();
    }

    /**
     * Called when the user taps the Send button
     */
    public void onBuildRouteClick(View view) {
        // create the Intent
        Intent intent = new Intent(this, RouteMap.class);
        Spinner first = findViewById(R.id.spinnerFirstStation);
        Spinner second = findViewById(R.id.spinnerSecondStation);

        Object st1 = first.getSelectedItem();
        if ( st1 == null )
            return;
        Object st2 = second.getSelectedItem();
        if ( st2 == null )
            return;

        String fistStation = st1.toString();
        String secondStation = st2.toString();

        String routeStr = buildRoute(fistStation, secondStation);

        // setup intent parameters. this is how we pass information between activities
        intent.putExtra(ROUTE_STRING, routeStr);

        // show BuildRoute activity
        startActivity(intent);
    }

    private String buildRoute(String fistStation, String secondStation) {

        // there is no need to call the path finder if we are not going anywhere
//        if (fistStation != secondStation) {
//        }

        // --------------------------------------------------------- find the path
        Graph nodeGraph = tube.generateGraph();
        // algorithm finds paths from every single point in the graph to the destination point
        nodeGraph.calculateShortestPathFromSource(fistStation); // runs the algorithm!
        // here we can get the path from any starting point
        // ---------------------------------------------------------

        // path was found above. simply get the route starting from secondStation
        List<Node> commuteRoute = new ArrayList<>(nodeGraph.getShortestPath(secondStation));

        // adds the last station to the list
        commuteRoute.add(nodeGraph.getNode(secondStation));

        // remove crossings for the same station at the beginning
        while (commuteRoute.size()>=2 && commuteRoute.get(0).getName().equals(commuteRoute.get(1).getName())) {
            commuteRoute.remove(0);
        }

        // remove crossings for the same station at the end
        if(commuteRoute.size()>=2) {
            while (commuteRoute.get(commuteRoute.size() - 1).getName().equals(commuteRoute.get(commuteRoute.size() - 2).getName())) {
                commuteRoute.remove(commuteRoute.size() - 1);
            }
        }

        return join(commuteRoute, ",");
    }

    // standard Android calls. Nothing has changed since added by wizard

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fast_pipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);

        } else if(id == R.id.nav_camera) {
            // Handle the camera action
        }else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
