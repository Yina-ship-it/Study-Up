package com.team4822.studyup.repositories.game;

import com.team4822.studyup.models.game.PlayerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerUserRepository extends JpaRepository<PlayerUser, Long> {
    Optional<PlayerUser> findPlayerUserByUser_Id(long userId);
}
