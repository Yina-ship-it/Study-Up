package com.team4822.studyup.models.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.team4822.studyup.models.authentication.User;
import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private GameType type;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "username"})
    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private User player1;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "username"})
    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private User player2;

    public Game(GameType type, GameStatus status, User player1) {
        this.type = type;
        this.status = status;
        this.player1 = player1;
    }

    public Game() {

    }

    public long getId() {
        return id;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User user) {
        this.player2 = user;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User user) {
        this.player1 = user;
    }
}
