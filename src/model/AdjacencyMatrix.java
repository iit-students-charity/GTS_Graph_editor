package model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class AdjacencyMatrix {
    private Graph graph;
    private Map<Node, Map<Node, Boolean>> adjacencyMatrix;


    public AdjacencyMatrix(Graph graph) {
        this.graph = graph;

        adjacencyMatrix = new HashMap<>();
        configureAdjacentNodesMatrix();
    }

    @Override
    public String toString() {
        String toString = "";

        for (Node node : adjacencyMatrix.keySet()) {
            for (Boolean isAdjacent : adjacencyMatrix.get(node).values()) {
                toString = toString.concat(isAdjacent ? "1 " : "0 ");
            }
            toString = toString.concat("\n");
        }

        return toString;
    }

    /*
        Configs
     */

    private void configureAdjacentNodesMatrix() {
        graph.getArcs().addListener((ListChangeListener) changeList -> {
            adjacencyMatrix.clear();
            for (Node node : graph.getNodes()) {
                adjacencyMatrix.put(node, setAdjacentNodesFor(node));
            }
        });

        graph.getNodes().addListener((ListChangeListener) changeList -> {
            adjacencyMatrix.clear();
            for (Node node : graph.getNodes()) {
                adjacencyMatrix.put(node, setAdjacentNodesFor(node));
            }
        });
    }

    /*
        Utility
     */

    private Map<Node, Boolean> setAdjacentNodesFor(Node node) {
        Map<Node, Boolean> adjacentNodes = new HashMap<>();

        for (Node graphNode : graph.getNodes()) {
            adjacentNodes.put(graphNode, false);
        }

        for (Node anotherGraphNode : graph.getNodes()) {
            adjacentNodes.replace(anotherGraphNode, isArcExist(node, anotherGraphNode));
        }

        return adjacentNodes;
    }

    private boolean isArcExist(Node begin, Node end) {
        for (Arc arc : graph.getArcs()) {
            if (arc.getBegin().equals(begin) && arc.getEnd().equals(end)) {
                return true;
            }
        }

        return false;
    }

    public ObservableList<Node> adjacentNodesOf(Node node) {
        ObservableList<Node> adjacents = FXCollections.observableArrayList();

        for (Node foundNode : adjacencyMatrix.get(node).keySet()) {
            if (adjacencyMatrix.get(node).get(foundNode)) {
                adjacents.add(foundNode);
            }
        }

        return adjacents;
    }
}
