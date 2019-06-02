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

    public final Graph generateGraph() {

        HashMap<Integer, Node> nodes = new HashMap<>();

        // ---------------------------------------------------------- create stations
        for (TubeStation station : getTubeStations()) {
            int stationsID;
            stationsID = station.getId();
            nodes.put(stationsID, new Node(station));
        }

        HashMap<Integer, TubeLine> lines = new HashMap<>();

        // ---------------------------------------------------------- create stations
        for (TubeLine line : getTubeLines()) {
            int lineID;
            lineID = line.getLine();
            lines.put(lineID, line);
        }
        // ---------------------------------------------------------- connect stations
        for (TubeConnection connection : getTubeConnections()) {
            int id1 = connection.getStation1();
            int id2 = connection.getStation2();
            int time = connection.getTime();


            Node node1 = nodes.get(id1);
            Node node2 = nodes.get(id2);

            node1 = adjustNode(lines, connection, node1);
            node2 = adjustNode(lines, connection, node2);


            node1.addDestination(node2, time); // forward path
            node2.addDestination(node1, time); // return path
        }

        Graph result = new Graph();

        // ---------------------------------------------------------- assemble the graph
        for (Node node : nodes.values()) {
            result.addNode(node);
        }

        return result;
    }

    private static Node adjustNode(HashMap<Integer, TubeLine> lines, TubeConnection connection, Node node1) {

        TubeLine line = lines.get(connection.getLine());

        //check if node is attached to line
        if ( node1.getLine() == null)
        {
            //if not attach it
            node1.setLine(line);
            return node1;
        }
        //already attached to the right line
        if ( node1.getLine() == line)
            return node1;

        HashMap<Integer,Node> sameStations = new HashMap<>();
        getSameStation(node1,sameStations);

        // this is crossing


        //check neighbours if the right station is already created
        for ( Node n : sameStations.values()) {
            if ( n.getLine() == line && n.getName().equals(node1.getName())) {
                return n;
            }
        }

        node1 = new Node( node1.getStation());

        for ( Node n : sameStations.values()) {
            n.addDestination(node1, 10);
            node1.addDestination(n, 10);
        }

        node1.setLine(line);
        return node1;
    }

    private static void getSameStation(Node station, HashMap<Integer,Node> stations){
        stations.put(station.getLine().getLine(),station);
        for ( Node n : station.getAdjacentNodes().keySet() ) {
            if (n.getName().equals(station.getName()) && !stations.containsKey(n.getLine().getLine())) {
                getSameStation(n,stations);
            }
        }
    }
}
