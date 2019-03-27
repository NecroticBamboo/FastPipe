package uk.co.fastpipe;

import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;
import uk.co.fastpipe.models.TubeReader;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class FastPipeActivityTest {

    @RunWith(AndroidJUnit4.class)


    @Test
    public void SouthQuay_To_Cyprus() throws IOException {
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();

        tube.calculateShortestPathFromSource("South Quay");
        List<Node> route = tube.getShortestPath("Cyprus");

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
