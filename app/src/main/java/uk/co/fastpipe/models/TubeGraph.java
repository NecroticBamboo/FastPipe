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

        if ( node1.getLine() == null)
        {
            node1.setLine(line);
            return node1;
        }

        // this is crossing
        Node oldNode = node1;

        for ( Node n : oldNode.getAdjacentNodes().keySet() ) {
            if ( n.getLine() == line && n.getName().equals(oldNode.getName())) {
                return n;
            }
        }

        if ( node1.getLine() == line)
            return node1;

        node1 = new Node( oldNode.getStation() );

        oldNode.addDestination(node1, 1);
        node1.addDestination(oldNode, 1);

        node1.setLine(line);
        return node1;
    }
}
