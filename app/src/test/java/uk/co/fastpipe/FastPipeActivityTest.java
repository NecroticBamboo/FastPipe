package uk.co.fastpipe;

import org.junit.Test;

import java.io.IOException;

public class FastPipeActivityTest {

    @Test
    public void Test1() throws IOException {


        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();

        tube.calculateShortestPathFromSource("Liverpool Street");
        List<Node> route = tube.getShortestPath("Canning town");

        assertEquals(10, route.size());

        // if you want to check the whole route do this: (list MUST contain all stations on the route)
        /*
        LinkedList<Node> expected = new LinkedList<>();
        expected.push( tube.getNode("South Quay"));
        expected.push( tube.getNode("Heron Quays"));
        expected.push( tube.getNode("Canary Wharf"));

        assertEquals(expected, route);
        */

        // alternatively you can check key stations only
        assertTrue(viaStation("South Quay", route));
        assertTrue(viaStation("North Greenwich", route));
    }

}
