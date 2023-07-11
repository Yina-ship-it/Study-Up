package com.team4822.studyup.models.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team4822.studyup.models.authentication.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("USER")
public class PlayerUser extends Player {
    private double rating;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PlayerUser(User user) {
        super(PlayerType.USER);
        this.user = user;
        this.rating = 0;
    }

    public PlayerUser() {
    }

    @Override
    String getName() {
        return user.getUsername();
    }

    public double getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    // @OneToMany(mappedBy = "player1")
    // private List<Game> games;

    // getters and setters...
}
