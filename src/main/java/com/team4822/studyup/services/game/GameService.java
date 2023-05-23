package com.team4822.studyup.services.game;

import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.game.GameMap;
import com.team4822.studyup.models.game.enums.GameStatus;
import com.team4822.studyup.models.game.enums.GameType;
import com.team4822.studyup.repositories.UserRepository;
import com.team4822.studyup.repositories.game.GameRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameMapService gameMapService;
    private final Queue<User> searchingUsers;

    @Inject
    public GameService(GameRepository gameRepository, UserRepository userRepository, GameMapService gameMapService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameMapService = gameMapService;
        this.searchingUsers = new LinkedList<>();
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

    public void addSearchingUsers(long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        searchingUsers.add(user);
    }

    public User searchOpponent(long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        if(!searchingUsers.isEmpty())
            return searchingUsers.poll();
        else{
            searchingUsers.add(user);
            return null;
        }
    }

    public void cancelSearch(long userId) throws Exception {
        User user = searchingUsers.stream().filter(us -> us.getId() == userId).findFirst().orElseThrow(() -> new Exception("User not found"));
        searchingUsers.remove(user);
    }

    public GameMap createGame(long player1Id, long player2Id) throws Exception {
        User player1 = userRepository.findById(player1Id)
                .orElseThrow(() -> new Exception("Player 1 not found"));
        User player2 = userRepository.findById(player2Id)
                .orElseThrow(() -> new Exception("Player 2 not found"));
        Game game = new Game(GameType.MULTI_PLAYER, GameStatus.CREATED, player1, player2);
        saveGame(game);
        return gameMapService.generateMap(game);
    }
}
