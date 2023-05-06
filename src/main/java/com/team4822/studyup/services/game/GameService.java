package com.team4822.studyup.services.game;

import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.repositories.game.GameRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Inject
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll(){
        return gameRepository.findAll();
    }

    public Game findById(long id) throws Exception {
        Optional<Game> game = gameRepository.findById(id);
        if(game.isEmpty())
            throw new Exception("Game not found!");
        return game.get();
    }

    public List<Game> findAllByUserId(long userId){
        return gameRepository.findAllByUserId(userId);
    }

    public void saveGame(Game game){
        gameRepository.save(game);
    }

    public void deleteGame(long id){
        gameRepository.deleteById(id);
    }
}
