package com.team4822.studyup.controllers.game.dto.out;

import com.team4822.studyup.models.game.Player;

public class InterimResultMessage extends Message{
    private Player player;
    private boolean isCorrectAnswer;

    public InterimResultMessage(Player player, boolean isCorrectAnswer) {
        super(MessageType.INTERIM);
        this.player = player;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }
}
