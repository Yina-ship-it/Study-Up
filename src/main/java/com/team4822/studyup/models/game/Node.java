package com.team4822.studyup.models.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.models.game.enums.NodeType;
import jakarta.persistence.*;

@Entity
public class Node {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private NodeType type;

    private int hp;

    private int difficulty;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "map_id")
    private GameMap map;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "player_id")
    private User player;

    public Node(User player, GameMap gameMap) {
        this.player = player;
        this.difficulty = 5;
        this.hp = 3;
        this.type = NodeType.BASE;
        this.map = gameMap;
    }

    public Node(int difficulty, GameMap gameMap){
        this.player = null;
        this.difficulty = difficulty;
        this.hp = 1;
        this.type = NodeType.POINT;
        this.map = gameMap;
    }

    public Node() {
    }

    public long getId() {
        return id;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }
}
