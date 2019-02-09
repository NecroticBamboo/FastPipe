package uk.co.fastpipe.models;

import uk.co.fastpipe.graph.Graph;

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
    public final TubeLine[] getTubeLines() {return tubeLines;}

    //TODO convert tubeGraph to Graph
    public final Graph genearteGraph() {
        return null;
    }
}
