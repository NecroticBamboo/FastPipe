package uk.co.fastpipe;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;
import uk.co.fastpipe.models.TubeReader;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RealTubeTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("uk.co.fastpipe", appContext.getPackageName());
    }

    private boolean viaStation(String name, List<Node> route) {
        for( Node n : route ) {
            if ( n.getName().equals(name) )
                return true;
        }
        return false;
    }


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
        assertTrue(viaStation("Canary Wharf", route));
        assertTrue(viaStation("North Greenwich", route));
    }

}
