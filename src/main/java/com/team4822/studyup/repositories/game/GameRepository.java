package com.team4822.studyup.repositories.game;

import com.team4822.studyup.models.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g WHERE g.player1.id = :userId OR g.player2.id = :userId")
    List<Game> findAllByUserId(@Param("userId") Long userId);
}
