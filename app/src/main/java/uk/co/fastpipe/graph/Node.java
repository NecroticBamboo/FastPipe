package uk.co.fastpipe.graph;

import uk.co.fastpipe.models.TubeLine;
import uk.co.fastpipe.models.TubeStation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;
    private TubeStation station;
    private TubeLine line;

    // destination node and edge weight
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(TubeStation station) {
        this.station = station;
    }

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public String getName() {
        return station.getName();
    }

    public TubeStation getStation(){
        return station;
    }

    public TubeLine getLine(){return line;}

    public int getColor(){return line.getColor();}

    public void clear() {
        setDistance(Integer.MAX_VALUE);
        setShortestPath(new LinkedList<Node>());
    }

    public void setLine(TubeLine line) {
        this.line = line;
    }
}
