package uk.co.fastpipe.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import uk.co.fastpipe.R;
import uk.co.fastpipe.graph.Node;

import java.util.ArrayList;

public class StationArrayAdapter extends ArrayAdapter<Node> {
    public StationArrayAdapter(Context context, ArrayList<Node> stations) {
        super(context, 0, stations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Node station = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.station_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvColour = convertView.findViewById(R.id.textViewLineColour);
        // Populate the data into the template view using the data object
        tvName.setText(station.getName());
        tvColour.setBackground(new ColorDrawable(station.getColor()));
        // Return the completed view to render on screen
        return convertView;
    }
}
