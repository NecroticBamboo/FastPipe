package uk.co.fastpipe.graph;

import java.util.*;

public class Graph {
    private HashMap<String, Node> nameToNode = new HashMap<String, Node>();
    private HashMap<Integer, Node> idToNode = new HashMap<Integer, Node>();

    public void addNode(Node nodeA) {
        nameToNode.put(nodeA.getName(), nodeA);
        idToNode.put(nodeA.getStation().getId(), nodeA);
    }

    public Node getNode(String name){
        return nameToNode.get(name);
    }

    public Node getNodeById(int id) {
        return idToNode.get(id);
    }

    public void calculateShortestPathFromSource(String name) {
        calculateShortestPathFromSource(getNode(name));
    }

    /**
     * https://www.baeldung.com/java-dijkstra
     * */
    private void calculateShortestPathFromSource(Node source) {
        clear();
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry < Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {

                Node adjacentNode  = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private void clear() {
        for (Node node: nameToNode.values()){
            node.clear();
        }
    }

    public List<Node> getShortestPath(String destination) {
        return getShortestPath(getNode(destination));
    }

    private List<Node> getShortestPath(Node destination) {
        return destination.getShortestPath();
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }


}
