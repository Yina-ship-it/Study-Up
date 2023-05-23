package com.team4822.studyup.services.game;

import com.team4822.studyup.models.game.Edge;
import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.game.GameMap;
import com.team4822.studyup.models.game.Node;
import com.team4822.studyup.repositories.game.EdgeRepository;
import com.team4822.studyup.repositories.game.GameMapRepository;
import com.team4822.studyup.repositories.game.NodeRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameMapService {
    private EdgeRepository edgeRepository;
    private NodeRepository nodeRepository;
    private GameMapRepository gameMapRepository;

    @Inject
    public GameMapService(EdgeRepository edgeRepository, NodeRepository nodeRepository, GameMapRepository gameMapRepository) {
        this.edgeRepository = edgeRepository;
        this.nodeRepository = nodeRepository;
        this.gameMapRepository = gameMapRepository;
    }

    public GameMap generateMap(Game game){
        GameMap map = new GameMap(game);
        gameMapRepository.save(map);

        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        nodes.add(new Node(game.getPlayer1(), map));
        nodes.add(new Node(game.getPlayer2(), map));
        for (int i = 0; i < 3; i++)
            nodes.add(new Node(1, map));
        for (int i = 0; i < 2; i++)
            nodes.add(new Node(3, map));

        int[][] pairs = {
                {0, 2},
                {2, 3},
                {3, 4},
                {2, 5},
                {5, 4},
                {2, 6},
                {6, 4},
                {4, 1}
        };
        for (int[] pair : pairs)
            edges.add(new Edge(nodes.get(pair[0]), nodes.get(pair[1])));

        nodeRepository.saveAll(nodes);
        edgeRepository.saveAll(edges);

        return map;
    }
}
