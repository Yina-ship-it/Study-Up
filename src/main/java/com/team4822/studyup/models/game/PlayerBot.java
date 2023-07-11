package com.team4822.studyup.models.game;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOT")
public class PlayerBot extends Player {
    private int difficulty;
    private String name;

    public PlayerBot(int difficulty, String name) {
        super(PlayerType.BOT);
        this.difficulty = difficulty;
        this.name = name;
    }

    public PlayerBot() {

    }

    @Override
    String getName() {
        return name;
    }
}
