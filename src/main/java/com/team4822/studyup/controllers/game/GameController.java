package com.team4822.studyup.controllers.game;

import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.services.game.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/gaming/game")
public class GameController {
    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all")
    public List<Game> getAllGames(){
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGame(@PathVariable long id){
        try{
            Game game = gameService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(game);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void removeAnswer(@PathVariable long id){
        gameService.deleteGame(id);
    }
}
