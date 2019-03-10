package uk.co.fastpipe.graph;

import org.junit.Test;
import uk.co.fastpipe.models.TubeStation;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void addDestinationForward() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        Node n = new Node(t1);
        Node m = new Node(t2);
        n.addDestination(m,1);
        Map<Node, Integer> exp = n.getAdjacentNodes();
        assertEquals(1,exp.size());
    }

    @Test
    public void addDestinationBackward() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        Node n = new Node(t1);
        Node m = new Node(t2);
        n.addDestination(m,1);
        Map<Node, Integer> exp = m.getAdjacentNodes();
        assertEquals(0,exp.size());
    }

    @Test
    public void addDestination1() {
     TubeStation t1= new TubeStation();
     TubeStation t2= new TubeStation();
     TubeStation t3= new TubeStation();
     Node n= new Node(t1);
     Node m= new Node(t2);
     Node p= new Node(t3);
     n.addDestination(m,2);
     m.addDestination(p,1);
     Map<Node, Integer> exp = n.getAdjacentNodes();
     Map<Node, Integer> exp2 = m.getAdjacentNodes();
     Map<Node, Integer> exp3 = p.getAdjacentNodes();
     assertEquals(1,exp2.size());
     assertEquals(1,exp.size());
     assertEquals(0,exp3.size());
    }

    @Test
    public void addDestination2() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        TubeStation t3= new TubeStation();
        Node n = new Node(t1);
        Node m = new Node(t2);
        Node p = new Node(t3);
        n.addDestination(m,1);
        m.addDestination(p,2);
        m.addDestination(n,1);
        p.addDestination(m,2);
        Map<Node, Integer> exp = m.getAdjacentNodes();
        assertEquals(2,exp.size());
    }

    @Test
    public void addDestination3() {
        TubeStation t1= new TubeStation();
        Node n = new Node(t1);
        n.addDestination(n,0);
        Map<Node, Integer> exp = n.getAdjacentNodes();
        assertEquals(1,exp.size());
    }
    @Test
    public void getDistance1() {
    TubeStation t1= new TubeStation();
    Node a= new Node(t1);
    int distance= a.getDistance();
    assertEquals(Integer.MAX_VALUE,distance);
    }

    @Test
    public void getDistance2() {
        TubeStation t1= new TubeStation();
        Node a= new Node(t1);
        a.setDistance(Integer.MIN_VALUE);
        int distance= a.getDistance();
        assertEquals(Integer.MIN_VALUE,distance);
    }

    @Test
    public void getDistance3() {
        TubeStation t1= new TubeStation();
        Node a= new Node(t1);
        a.setDistance(1);
        int distance= a.getDistance();
        assertEquals(1,distance);
    }

    @Test
    public void getDistance4() {
        TubeStation t1= new TubeStation();
        Node a= new Node(t1);
        a.setDistance(0);
        int distance= a.getDistance();
        assertEquals(0,distance);
    }

    @Test
    public void getShortestPath1() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        TubeStation t3= new TubeStation();
        Node n= new Node(t1);
        Node m= new Node(t2);
        Node p= new Node(t3);
        LinkedList<Node> list=new LinkedList<Node>();
        list.push(m);
        list.push(p);
        n.setShortestPath(list);
        assertEquals(list,n.getShortestPath());
    }

    @Test
    public void getShortestPath2() {
        TubeStation t1= new TubeStation();
        Node n= new Node(t1);
        LinkedList<Node> list=new LinkedList<Node>();
        n.setShortestPath(list);
        assertEquals(list,n.getShortestPath());
    }

    @Test
    public void getShortestPath3() {
        TubeStation t1= new TubeStation();
        Node n= new Node(t1);
        assertNotNull(n.getShortestPath());
    }

    @Test
    public void getShortestPath4() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        Node n= new Node(t1);
        Node m= new Node(t2);
        LinkedList<Node> list=new LinkedList<Node>();
        list.push(m);
        n.setShortestPath(list);
        assertEquals(list,n.getShortestPath());
    }

    @Test
    public void getShortestPath5() {
        TubeStation t1= new TubeStation();
        TubeStation t2= new TubeStation();
        TubeStation t3= new TubeStation();
        TubeStation t4= new TubeStation();
        Node n= new Node(t1);
        Node m= new Node(t2);
        Node p= new Node(t3);
        Node k= new Node(t4);
        LinkedList<Node> list=new LinkedList<Node>();
        list.push(m);
        list.push(p);
        list.push(k);
        n.setShortestPath(list);
        assertEquals(list,n.getShortestPath());
    }

    @Test
    public void getName1() {
        TubeStation t1 = new TubeStation(1,0,0,"Bank","Bank",1,1,false);
        Node n = new Node(t1);
        assertEquals("Bank",n.getName());
    }

    @Test
    public void getName2() {
        TubeStation t1 = new TubeStation();
        Node n = new Node(t1);
        assertNull(n.getName());
    }

    @Test
    public void getName3() {
        TubeStation t1 = new TubeStation(2,0,0,"Canada Water","Canada Water",1,1,false);
        Node n = new Node(t1);
        assertEquals("Canada Water",n.getName());
    }

    @Test
    public void clear() {
        TubeStation t1 = new TubeStation();
        Node n = new Node(t1);
        n.clear();
        assertEquals(Integer.MAX_VALUE,(int)n.getDistance());
        assertNotNull(n.getShortestPath());
    }

}