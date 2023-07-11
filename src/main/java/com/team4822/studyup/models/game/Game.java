package com.team4822.studyup.models.game;

import com.team4822.studyup.models.quiz.Topic;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "player1_id")
    private Player player1;

    private int score1;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Player player2;

    private int score2;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private int currentCycles;
    private int maxCycles;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;

    public Game(Player player1, Player player2, Topic topic, int maxCycles) {
        this.player1 = player1;
        this.player2 = player2;
        this.topic = topic;
        this.status = Status.STARTED;
        this.maxCycles = maxCycles;
        this.currentCycles = 0;

        this.winner = null;
        this.score1 = 0;
        this.score2 = 0;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Game() {
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public long getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Topic getTopic() {
        return topic;
    }

    public int getMaxCycles() {
        return maxCycles;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCurrentCycles() {
        return currentCycles;
    }

    public void setCurrentCycles(int currentCycles) {
        this.currentCycles = currentCycles;
    }


    public Player getWinner() {
        return winner;
    }

    public enum Status {
        STARTED, FINISHED, ERROR
    }
}
