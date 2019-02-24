package uk.co.fastpipe.graph;

import org.junit.Test;
import uk.co.fastpipe.models.TubeStation;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Test
    public void addDestination() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        Node n = new Node(t1);
        Node m = new Node(t2);
        n.addDestination(m,1);
        Map<Node, Integer> exp = n.getAdjacentNodes();
        assertEquals(1,exp.size());
    }

    @Test
    public void getDistance() {
    }

    @Test
    public void setDistance() {
    }

    @Test
    public void getAdjacentNodes() {
    }

    @Test
    public void getShortestPath() {
    }

    @Test
    public void setShortestPath() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void clear() {
    }
}