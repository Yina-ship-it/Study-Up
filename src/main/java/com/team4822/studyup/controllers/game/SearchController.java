package com.team4822.studyup.controllers.game;

import com.team4822.studyup.controllers.game.dto.in.SearchMessage;
import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.game.PlayerUser;
import com.team4822.studyup.services.game.SearchingGameService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

@Controller
public class SearchController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SearchingGameService searchingGameService;

    @Inject
    public SearchController(SimpMessagingTemplate simpMessagingTemplate, SearchingGameService searchingGameService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.searchingGameService = searchingGameService;
    }

    @SubscribeMapping("/search/{id}")
    public void handleSearchSubscription(@DestinationVariable long id) {
        System.out.println("Player " + id + " subscribed to the game search channel");
    }

    @MessageMapping("/search/start")
    public void startSearching(SearchMessage msg){
        System.out.println(1);
        try{
            PlayerUser opponent = searchingGameService.searchOpponent(msg.getPlayerId());
            if(opponent != null){
                Game game = searchingGameService.createGame(msg.getPlayerId(), opponent.getId(), msg.getTopicId());
                simpMessagingTemplate.convertAndSend("/topic/search/" + opponent.getId(), game.getId());
                simpMessagingTemplate.convertAndSend("/topic/search/" + msg.getPlayerId(), game.getId());
            }
            else
                searchingGameService.addSearchingUsers(msg.getPlayerId());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @MessageMapping("/search/cancel")
    public void cancelSearching(SearchMessage msg){
        try {
            searchingGameService.cancelSearch(msg.getPlayerId());
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
