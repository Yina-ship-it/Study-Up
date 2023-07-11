package com.team4822.studyup.controllers.game.dto;

import com.team4822.studyup.models.game.Player;

public class Node {
    private int id;
    private NodeType type;
    private int difficulty;
    private int hp;
    private Player player;

    public Node(int id, int difficulty){
        this.id = id;
        this.type = NodeType.POINT;
        this.difficulty = difficulty;
        this.hp = 1;
        this.player = null;
    }

    public Node(int id, Player player) {
        this.id = id;
        this.type = NodeType.BASE;
        this.difficulty = 3;
        this.hp = 3;
        this.player = player;
    }

    public boolean isBase(){
        return type == NodeType.BASE;
    }

    public boolean isPoint(){
        return type == NodeType.POINT;
    }

    public int getId() {
        return id;
    }

    public NodeType getType() {
        return type;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getHp() {
        return hp;
    }

    public Player getPlayer() {
        return player;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public enum NodeType {
        BASE, POINT
    }
}
