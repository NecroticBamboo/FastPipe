package uk.co.fastpipe.models;

public class TubeGraph {
    TubeStation[] tubestations;

    public TubeGraph(TubeStation[] tubestations) {
        this.tubestations = tubestations;
    }

    public final TubeStation[] getTubeStations() {
        return tubestations;
    }

}
