package com.team4822.studyup.controllers.game.dto.out;

import com.team4822.studyup.controllers.game.dto.GameMap;
import com.team4822.studyup.models.game.Player;

public class ResultGameMessage extends Message{
    private Player winner;
    private GameMap map;


    public ResultGameMessage(Player winner, GameMap map) {
        super(MessageType.GAME_RESULT);
        this.winner = winner;
        this.map = map;
    }

    public Player getWinner() {
        return winner;
    }

    public GameMap getMap() {
        return map;
    }
}
