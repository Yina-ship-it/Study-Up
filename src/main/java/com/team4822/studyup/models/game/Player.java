package com.team4822.studyup.models.game;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "player_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Player {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_type", insertable = false, updatable = false)
    private PlayerType type;

    public Player(PlayerType type) {
        this.type = type;
    }

    public Player() {

    }

    public enum PlayerType{
        BOT, USER
    }

    abstract String getName();

    public long getId() {
        return id;
    }

    public PlayerType getType() {
        return type;
    }
}
