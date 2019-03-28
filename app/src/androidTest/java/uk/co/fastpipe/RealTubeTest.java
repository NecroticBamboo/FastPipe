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
import java.util.LinkedList;
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

    @Test
    public void Liverpoolstreet_to_Canningtown() throws IOException { //Kierans Test
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();
        tube.calculateShortestPathFromSource("Liverpool Street");
        List<Node> route = tube.getShortestPath("Canning Town");

        assertEquals(6, route.size());


        LinkedList<Node> expected = new LinkedList<>();
        expected.push( tube.getNode("Liverpool Street"));
        expected.push( tube.getNode("Bethnal Green"));
        expected.push( tube.getNode("Mile End"));
        expected.push( tube.getNode("Bow Road"));
        expected.push( tube.getNode("Bromley By Bow"));
        expected.push( tube.getNode("West Ham"));
        expected.push( tube.getNode("Canning Town"));


    }

    @Test
    public void Cyprus_to_Cyprus() throws IOException { //Kierans Test
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();

        tube.calculateShortestPathFromSource("Cyprus");
        List<Node> route = tube.getShortestPath("Cyprus");

        assertEquals(0, route.size());

        LinkedList<Node> expected = new LinkedList<>();
        expected.push( tube.getNode("Cyprus"));
    }

    @Test
    public void Kensington_to_Cyprus() throws IOException { //Kierans Test
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();
        tube.calculateShortestPathFromSource("Kensington (Olympia)");
        List<Node> route = tube.getShortestPath("Cyprus");

        assertEquals(21, route.size());


        LinkedList<Node> expected = new LinkedList<>();
        expected.push(tube.getNode("Kensington (Olympia)"));
        expected.push(tube.getNode("Earl's Court"));
        expected.push(tube.getNode("Gloucester Road"));
        expected.push(tube.getNode("South Kensington"));
        expected.push(tube.getNode("Sloane Square"));
        expected.push(tube.getNode("Victoria"));
        expected.push(tube.getNode("St.James's Park"));
        expected.push(tube.getNode("Westminster"));
        expected.push(tube.getNode("Waterloo"));
        expected.push(tube.getNode("Southwark"));
        expected.push(tube.getNode("London Bridge"));
        expected.push(tube.getNode("Bermondsey"));
        expected.push(tube.getNode("Canada Water"));
        expected.push(tube.getNode("Canary Wharf"));
        expected.push(tube.getNode("North Greenwich"));
        expected.push(tube.getNode("Canning Town"));
        expected.push(tube.getNode("Royal Victoria"));
        expected.push(tube.getNode("Custom House"));
        expected.push(tube.getNode("Prince Regent"));
        expected.push(tube.getNode("Cyprus"));
    }


        @Test
    public void Bank_to_Cyprus() throws IOException {
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();

        tube.calculateShortestPathFromSource("Bank");
        List<Node> route = tube.getShortestPath("Cyprus");

        assertEquals(13, route.size());

        assertTrue(viaStation("Bank", route));
        assertTrue(viaStation("Shadwell", route));
        assertTrue(viaStation("Canning Town", route));
        assertTrue(viaStation("Beckton Park", route));
    }

    @Test
    public void Westminster_to_Westminster() throws IOException {
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();

        tube.calculateShortestPathFromSource("Westminster");
        List<Node> route = tube.getShortestPath("Westminster");

        assertEquals(0, route.size());
    }

    @Test
    public void SouthQuay_to_Richmond() throws IOException {
        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();

        tube.calculateShortestPathFromSource("South Quay");
        List<Node> route = tube.getShortestPath("Richmond");

        assertEquals(20, route.size());

        assertTrue(viaStation("Canary Wharf", route));
        assertTrue(viaStation("Waterloo", route));
        assertTrue(viaStation("South Kensington", route));
        assertTrue(viaStation("Westminster", route));
        assertTrue(viaStation("Hammersmith", route));
        assertTrue(viaStation("Gunnersbury", route));
    }

//    @Test
//    public void Westferry_to_Mudchute() throws IOException {
//        Graph tube = TubeReader.load(InstrumentationRegistry.getTargetContext()).generateGraph();
//
//        tube.calculateShortestPathFromSource("Westferry");
//        List<Node> route = tube.getShortestPath("Mudchute");
//
//        LinkedList<Node> expected = new LinkedList<>();
//        expected.push( tube.getNode("Crossharbour"));
//        expected.push( tube.getNode("South Quay"));
//        expected.push( tube.getNode("Heron Quays"));
//        expected.push( tube.getNode("Canary Wharf"));
//        expected.push( tube.getNode("West India Quay"));
//        expected.push( tube.getNode("Westferry"));
//
//        assertEquals(expected, route);
//    }
}
