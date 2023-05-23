package com.team4822.studyup.models.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.models.game.enums.GameStatus;
import com.team4822.studyup.models.game.enums.GameType;
import com.team4822.studyup.models.quiz.Topic;
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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private User player1;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private User player2;

    @OneToOne(mappedBy = "game")
    private GameMap map;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Game(GameType type, GameStatus status, User player1, User player2) {
        this.type = type;
        this.status = status;
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
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
