package com.team4822.studyup.services.game;

import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.game.Player;
import com.team4822.studyup.models.game.PlayerUser;
import com.team4822.studyup.repositories.game.PlayerUserRepository;
import com.team4822.studyup.services.quiz.TopicService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SearchingGameService {
    private final PlayerUserRepository playerRepository;
    private final GameService gameService;
    private final TopicService topicService;

    @Inject
    public SearchingGameService(PlayerUserRepository playerRepository, GameService gameService, TopicService topicService) {
        this.playerRepository = playerRepository;
        this.gameService = gameService;
        this.topicService = topicService;
    }

    public void addSearchingUsers(long playerId) throws Exception {
        PlayerUser player = playerRepository.findById(playerId).orElseThrow(() -> new Exception("User not found"));
        SearchQueue.getInstance().addUser(player);
    }

    public PlayerUser searchOpponent(long playerId) throws Exception {
        PlayerUser player = playerRepository.findById(playerId).orElseThrow(() -> new Exception("Player not found"));
        if(!SearchQueue.getInstance().getSearchingUsers().isEmpty())
            return SearchQueue.getInstance().pollUser();
        else{
            SearchQueue.getInstance().addUser(player);
            return null;
        }
    }

    public void cancelSearch(long playerId) throws Exception {
        Player canceler = playerRepository.findById(playerId).orElseThrow(() -> new Exception("User not found"));
        SearchQueue.getInstance().removeUser(
                SearchQueue.getInstance().getSearchingUsers().stream()
                .filter(pl -> pl.getId() == canceler.getId())
                .findFirst().orElseThrow(() -> new Exception("User not found"))
        );
    }

    public Game createGame(long player1Id, long player2Id, long topicId) throws Exception {
        Player player1 = playerRepository.findById(player1Id).orElseThrow(() -> new Exception("User not found"));
        Player player2 = playerRepository.findById(player2Id).orElseThrow(() -> new Exception("User not found"));
        Game game = new Game(player1, player2, topicService.findById(topicId), 10);
        gameService.saveGame(game);
        return game;
    }
}

