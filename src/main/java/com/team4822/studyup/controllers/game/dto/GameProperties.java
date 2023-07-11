package com.team4822.studyup.controllers.game.dto;

import com.team4822.studyup.models.game.Player;

import java.util.Map;

public class GameProperties {
    private int cyclesCount;
    private Player currentPlayer;
    private Node currentNode;
    private long currentQuestionId;
    private Map<Long, Boolean> playerCorrectAnswer;
    private int questionCount;
    private int currentDifficulty;

    public GameProperties(Player currentPlayer) {
        this.cyclesCount = 0;
        this.currentPlayer = currentPlayer;
        this.currentNode = null;
        this.currentQuestionId = -1;
        this.playerCorrectAnswer = null;
        this.questionCount = 0;
        this.currentDifficulty = 0;
    }

    public int getCyclesCount() {
        return cyclesCount;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public long getCurrentQuestionId() {
        return currentQuestionId;
    }

    public Map<Long, Boolean> getPlayerCorrectAnswer() {
        return playerCorrectAnswer;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setCyclesCount(int cyclesCount) {
        this.cyclesCount = cyclesCount;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public void setCurrentQuestionId(long currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public void setPlayerCorrectAnswer(Map<Long, Boolean> playerCorrectAnswer) {
        this.playerCorrectAnswer = playerCorrectAnswer;
    }

    public int getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(int currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
}
