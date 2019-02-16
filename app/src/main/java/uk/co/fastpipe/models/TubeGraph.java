package uk.co.fastpipe.models;

import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;

import java.util.HashMap;

public class TubeGraph {
    TubeStation[] tubeStations;
    TubeConnection[] tubeConnections;
    TubeLine[] tubeLines;

    public TubeGraph(TubeStation[] tubeStations, TubeConnection[] tubeConnections, TubeLine[] tubeLines) {
        this.tubeStations = tubeStations;
        this.tubeConnections = tubeConnections;
        this.tubeLines = tubeLines;

    }

    public final TubeStation[] getTubeStations() {
        return tubeStations;
    }

    public final TubeConnection[] getTubeConnections() {
        return tubeConnections;
    }

    public final TubeLine[] getTubeLines() {
        return tubeLines;
    }

    public final Graph genearteGraph() {

        HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();

        // ---------------------------------------------------------- create stations
        for (TubeStation station : getTubeStations()) {
            int stationsID;
            stationsID = station.getId();
            nodes.put(stationsID, new Node(station.getName()));
        }

        // ---------------------------------------------------------- connect stations
        for (TubeConnection connection : getTubeConnections()) {
            int id1 = connection.getStation1();
            int id2 = connection.getStation2();
            int time = connection.getTime();

            Node node1 = nodes.get(id1);
            Node node2 = nodes.get(id2);

            node1.addDestination(node2, time);
            node2.addDestination(node1, time);
        }

        Graph result = new Graph();

        // ---------------------------------------------------------- assemble the graph
        for (Node node : nodes.values()) {
            result.addNode(node);
        }

        return result;
    }
}
