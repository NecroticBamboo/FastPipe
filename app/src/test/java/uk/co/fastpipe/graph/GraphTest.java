package uk.co.fastpipe.graph;

import org.junit.Test;
import uk.co.fastpipe.models.TubeStation;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GraphTest {

    @Test
    public void addNode() {
    }

    @Test
    public void oneNode() {
//             *-X
        TubeStation t1= new TubeStation(13,51.5133,-0.0886,"Bank","Bank",1,4,false);
        TubeStation t2= new TubeStation(19,51.5148,0.0613,"Beckton","Beckton",3,1,false);
        Node start = new Node(t1);
        Node finish = new Node(t2);
        start.addDestination(finish,3);
        Graph graph = new Graph();
        graph.addNode(start);
        graph.addNode(finish);

        graph.calculateShortestPathFromSource("Bank");
        List<Node> answer = graph.getShortestPath("Beckton");

        assertEquals(1,answer.size());
        assertEquals("Bank",answer.get(0).getName());
    }

    @Test
    public void twoNodes(){
//            S-1-F
        TubeStation t1= new TubeStation(13,51.5133,-0.0886,"Bank","Bank",1,4,false);
        TubeStation t2= new TubeStation(19,51.5148,0.0613,"Beckton","Beckton",3,1,false);
        TubeStation t3= new TubeStation(4,4,4,"Finish","Finish",2,3,false);
        Node start = new Node(t1);
        Node s1 = new Node(t2);
        Node finish = new Node(t3);
        start.addDestination(s1,3);
        s1.addDestination(finish,4);
        Graph graph = new Graph();
        graph.addNode(start);
        graph.addNode(finish);
        graph.addNode(s1);

        graph.calculateShortestPathFromSource("Bank");
        List<Node> answer = graph.getShortestPath("Finish");

        assertEquals(2,answer.size());
        assertEquals("Bank",answer.get(0).getName());
        assertEquals("Beckton",answer.get(1).getName());
    }

    @Test
    public void oneCross(){
//            /F
//         S-1-2
        TubeStation t1= new TubeStation(13,51.5133,-0.0886,"Bank","Bank",1,4,false);
        TubeStation t2= new TubeStation(19,51.5148,0.0613,"Beckton","Beckton",3,1,false);
        TubeStation t3= new TubeStation(4,4,4,"Finish","Finish",2,3,false);
        TubeStation t4= new TubeStation(2,2,2,"T","T",2,3,false);
        Node start = new Node(t1);
        Node s1 = new Node(t2);
        Node finish = new Node(t3);
        Node s2 = new Node(t4);
        start.addDestination(s1,3);
        s1.addDestination(finish,4);
        s1.addDestination(s2,5);
        Graph graph = new Graph();
        graph.addNode(start);
        graph.addNode(finish);
        graph.addNode(s1);
        graph.addNode(s2);

        graph.calculateShortestPathFromSource("Bank");
        List<Node> answer = graph.getShortestPath("Finish");

        assertEquals(2,answer.size());
        assertEquals("Bank",answer.get(0).getName());
        assertEquals("Beckton",answer.get(1).getName());
    }

    @Test
    public void twoCrosses(){
//              /F
//            /3-4
//         S-1-2
        TubeStation t1= new TubeStation(13,51.5133,-0.0886,"Bank","Bank",1,4,false);
        TubeStation t2= new TubeStation(19,51.5148,0.0613,"Beckton","Beckton",3,1,false);
        TubeStation t3= new TubeStation(1,1,1,"R","R",1,3,false);
        TubeStation t4= new TubeStation(2,2,2,"T","T",2,3,false);
        TubeStation t5= new TubeStation(3,3,3,"Y","Y",1,3,false);
        TubeStation t6= new TubeStation(4,4,4,"Finish","Finish",2,3,false);
        Node start = new Node(t1);
        Node s1 = new Node(t2);
        Node s2 = new Node(t3);
        Node s3 = new Node(t4);
        Node s4 = new Node(t5);
        Node finish = new Node(t6);
        start.addDestination(s1,3);
        s1.addDestination(s2,2);
        s1.addDestination(s3,1);
        s3.addDestination(s4,3);
        s3.addDestination(finish,4);
        Graph graph = new Graph();
        graph.addNode(start);
        graph.addNode(finish);
        graph.addNode(s1);
        graph.addNode(s2);
        graph.addNode(s3);
        graph.addNode(s4);

        graph.calculateShortestPathFromSource("Bank");
        List<Node> answer = graph.getShortestPath("Finish");

        assertEquals(3,answer.size());
        assertEquals("Bank",answer.get(0).getName());
        assertEquals("Beckton",answer.get(1).getName());
        assertEquals("T",answer.get(2).getName());
    }

    @Test
    public void Fork(){
        //      F 6 7
        //      \ | /
        //        5
        //       / \
        //      1   4
        //     / \  |
        //    S   2 3
        TubeStation t1= new TubeStation(13,51.5133,-0.0886,"Start","Start",1,4,false);
        TubeStation t2= new TubeStation(19,51.5148,0.0613,"A","A",3,1,false);
        TubeStation t3= new TubeStation(1,1,1,"B","B",1,3,false);
        TubeStation t4= new TubeStation(2,2,2,"C","C",2,3,false);
        TubeStation t5= new TubeStation(3,3,3,"D","D",1,3,false);
        TubeStation t6= new TubeStation(4,4,4,"E","E",2,3,false);
        TubeStation t7= new TubeStation(5,5,5,"F","F",2,3,false);
        TubeStation t8= new TubeStation(6,6,6,"G","G",2,3,false);
        TubeStation t9= new TubeStation(7,7,7,"Finish","Finish",2,3,false);
        Node start = new Node(t1);
        Node s1 = new Node(t2);
        Node s2 = new Node(t3);
        Node s3 = new Node(t4);
        Node s4 = new Node(t5);
        Node s5 = new Node(t6);
        Node s6 = new Node(t7);
        Node s7 = new Node(t8);
        Node finish = new Node(t9);
        start.addDestination(s1,3);
        s1.addDestination(s2,2);
        s1.addDestination(s5,4);
        s5.addDestination(s4,6);
        s4.addDestination(s3,2);
        s5.addDestination(s6,3);
        s5.addDestination(s7,5);
        s5.addDestination(finish,6);
        Graph graph = new Graph();
        graph.addNode(start);
        graph.addNode(finish);
        graph.addNode(s1);
        graph.addNode(s2);
        graph.addNode(s3);
        graph.addNode(s4);
        graph.addNode(s5);
        graph.addNode(s6);
        graph.addNode(s7);

        graph.calculateShortestPathFromSource("Start");
        List<Node> answer = graph.getShortestPath("Finish");

        assertEquals(3,answer.size());
        assertEquals("Start",answer.get(0).getName());
        assertEquals("A",answer.get(1).getName());
        assertEquals("E",answer.get(2).getName());

    }

    @Test
    public void getShortestpath() {
    }
}