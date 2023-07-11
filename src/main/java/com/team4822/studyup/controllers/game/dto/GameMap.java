package com.team4822.studyup.controllers.game.dto;

import java.util.*;

public class GameMap {
    private final List<Node> nodes;
    private final Map<Integer, Set<Integer>> edges;

    public GameMap() {
        nodes = new ArrayList<>();
        edges = new HashMap<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public Map<Integer, Set<Integer>> getEdges() {
        return edges;
    }

    public void addEdge(int node1Id, int node2Id) {
        if (!edges.containsKey(node1Id))
            edges.put(node1Id, new HashSet<>());
        if (!edges.containsKey(node2Id))
            edges.put(node2Id, new HashSet<>());

        edges.get(node1Id).add(node2Id);
        edges.get(node2Id).add(node1Id);
    }
}
