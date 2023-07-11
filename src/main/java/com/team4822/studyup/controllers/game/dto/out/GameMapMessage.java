package com.team4822.studyup.controllers.game.dto.out;

import com.team4822.studyup.controllers.game.dto.GameMap;
import com.team4822.studyup.controllers.game.dto.Node;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMapMessage extends Message{
    private final List<Node> nodes;
    private final Map<Integer, Set<Integer>> edges;

    public GameMapMessage(GameMap gameMap) {
        super(MessageType.MAP);
        nodes = gameMap.getNodes();
        edges = gameMap.getEdges();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Map<Integer, Set<Integer>> getEdges() {
        return edges;
    }
}
