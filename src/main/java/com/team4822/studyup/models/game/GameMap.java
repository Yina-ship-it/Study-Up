package com.team4822.studyup.models.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "game_maps")
public class GameMap {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "map")
    private List<Node> nodes;

    @OneToMany(mappedBy = "map")
    private List<Edge> edges;

    @OneToOne
    @JoinColumn(name = "game_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Game game;

    public GameMap(Game game) {
        this.game = game;
    }

    public GameMap() {
    }

    public long getId() {
        return id;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
