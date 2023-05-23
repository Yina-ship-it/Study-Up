package com.team4822.studyup.controllers.game;

import com.team4822.studyup.controllers.game.dto.AnswerMessage;
import com.team4822.studyup.controllers.game.dto.CaptureMessage;
import com.team4822.studyup.models.authentication.User;
import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.game.GameMap;
import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.services.UserService;
import com.team4822.studyup.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GameMessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @MessageMapping("/search/start")
    public void findGame(User user){
        try {
            User opponent = gameService.searchOpponent(user.getId());
            if(opponent != null){
                GameMap map = gameService.createGame(user.getId(), opponent.getId());
                simpMessagingTemplate.convertAndSend("/topic/search/" + opponent.getId(), map);
                simpMessagingTemplate.convertAndSend("/topic/search/" + user.getId(), map);
            }
            else
                gameService.addSearchingUsers(user.getId());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @MessageMapping("/search/cancel")
    public void cancelSearch(User user) {
        try {
            gameService.cancelSearch(user.getId());
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @MessageMapping("/capture-game/{id}")
    public void capture(@DestinationVariable long id, CaptureMessage message){

    }

    @MessageMapping("/answer-game/{id}")
    public void answer(@DestinationVariable long id, AnswerMessage message){

    }

    @SubscribeMapping("/points/{id}")
    public void handlePointsSubscription(@DestinationVariable long id) {
        System.out.println("User subscribed to the channel with points of game: " + id);
        // Выводите нужную вам команду в консоль или выполняйте требуемые действия
    }

    @SubscribeMapping("/questions/{id}")
    public void handleQuestionsSubscription(@DestinationVariable long id) {
        System.out.println("User subscribed to the channel with questions of game: " + id);
        // Выводите нужную вам команду в консоль или выполняйте требуемые действия
    }

    @SubscribeMapping("/search/{id}")
    public void handlSearchSubscription(@DestinationVariable long id) {
        System.out.println("User " + id + " subscribed to the game search channel");
        // Выводите нужную вам команду в консоль или выполняйте требуемые действия
    }
}
