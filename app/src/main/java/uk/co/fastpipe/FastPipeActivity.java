package uk.co.fastpipe;

import android.content.Intent;
import android.os.Bundle;
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

    private TubeGraph tube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_pipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        try {
            tube = TubeReader.load(this);
            TubeStation[] stations = tube.getTubeStations();
            ArrayList<String> spinnerArray = new ArrayList<String>();

            for (int i = 0; i < stations.length; i++) {
                spinnerArray.add(stations[i].getName());
            }

            SetUpSinner(spinnerArray, R.id.spinnerFirstStation, R.id.editTextFirstSation);

            SetUpSinner(spinnerArray, R.id.spinnerSecondStation, R.id.editTextSecondStation);

        } catch (IOException ex) {

        }

    }

    private void SetUpSinner(ArrayList<String> spinnerArray, int spinnerSecondStation, int editTextSecondStation) {
        final Spinner spinner = findViewById(spinnerSecondStation);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        spinner.setAdapter(adapter);

        final EditText editText2 = findViewById(editTextSecondStation);
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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static String FIRST_STATION  = "first_station";
    public static String SECOND_STATION = "second_station";
    public static String ROUTE_STRING   = "route_string";

    /**
     * Join stationID as String
     * @param list
     * @param conjunction
     * @return
     */
    static public String join(List<Node> list, String conjunction)  //TODO write test in FastPipeActivityTest
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Node item : list)
        {
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
        Intent intent = new Intent(this, BuildRoute.class);
        Spinner first = findViewById(R.id.spinnerFirstStation);
        Spinner second = findViewById(R.id.spinnerSecondStation);

        String fistStation = first.getSelectedItem().toString();
        String secondStation = second.getSelectedItem().toString();

        Graph nodeGraph = tube.generateGraph();
        nodeGraph.calculateShortestPathFromSource(fistStation);

        List<Node> commuteRoute = nodeGraph.getShortestPath(secondStation);
        String routeStr = join(commuteRoute,",");

        intent.putExtra(FIRST_STATION, fistStation);
        intent.putExtra(SECOND_STATION, secondStation);
        intent.putExtra(ROUTE_STRING, routeStr);

        startActivity(intent);
    }

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
