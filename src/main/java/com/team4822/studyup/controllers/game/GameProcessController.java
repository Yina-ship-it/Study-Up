package com.team4822.studyup.controllers.game;

import com.team4822.studyup.controllers.game.dto.GameMap;
import com.team4822.studyup.controllers.game.dto.GameProperties;
import com.team4822.studyup.controllers.game.dto.Node;
import com.team4822.studyup.controllers.game.dto.in.AnswerMessage;
import com.team4822.studyup.controllers.game.dto.in.CaptureMessage;
import com.team4822.studyup.controllers.game.dto.out.InterimResultMessage;
import com.team4822.studyup.controllers.game.dto.out.QuestionMessage;
import com.team4822.studyup.controllers.game.dto.out.ResultGameMessage;
import com.team4822.studyup.controllers.game.dto.out.ResultMessage;
import com.team4822.studyup.models.game.Game;
import com.team4822.studyup.models.game.Player;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.services.game.GameService;
import com.team4822.studyup.services.quiz.QuestionService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.HashMap;

@Controller
public class GameProcessController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameService gameService;
    private final QuestionService questionService;

    @Inject
    public GameProcessController(SimpMessagingTemplate simpMessagingTemplate, GameService gameService, QuestionService questionService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.gameService = gameService;
        this.questionService = questionService;
    }

    @SubscribeMapping("/game/{id}")
    public void handleGameSubscription(@DestinationVariable long id, StompHeaderAccessor stompHeaderAccessor) throws Exception {
        try {
            Game game = gameService.findById(id);
            GameMap map = generateGameMap(game);
            stompHeaderAccessor.getSessionAttributes().put("map", map);
            stompHeaderAccessor.getSessionAttributes().put("properties", new GameProperties(game.getPlayer1()));
            System.out.println("User subscribed to the game " + id + " channel");
            simpMessagingTemplate.convertAndSend("/topic/game/" +id, map);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @MessageMapping("/game/{id}/capture")
    public void capture(@DestinationVariable long id, CaptureMessage msg, StompHeaderAccessor stompHeaderAccessor){
        try {
            Game game = gameService.findById(id);
            GameMap map = (GameMap) stompHeaderAccessor.getSessionAttributes().get("map");
            GameProperties properties = (GameProperties) stompHeaderAccessor.getSessionAttributes().get("properties");
            if(properties.getCurrentPlayer().getId() != msg.getPlayerId())
                throw new Exception();
            Node node = map.getNodes().stream()
                    .filter(node1 -> node1.getId() == msg.getNodeId())
                    .findFirst()
                    .orElseThrow(() -> new Exception());
            //Нужна проверка на возможность атаки
            boolean notBelongNode = map.getNodes().stream()
                    .anyMatch(node1 -> node1.getId() == msg.getNodeId() && node1.getPlayer().getId() != msg.getPlayerId());
            boolean hasConnectionNode = map.getEdges().get(msg.getNodeId()).stream()
                    .anyMatch(nodeId ->
                        map.getNodes().stream().anyMatch(node1 -> node1.getPlayer().getId() != msg.getPlayerId())
                    );
            if (!notBelongNode || !hasConnectionNode)
                throw new Exception("You can't go to this node");

            Question question = questionService.findRandomQuestion(game.getTopic(), node.getDifficulty());
            System.out.println(question);
            properties.setCurrentNode(node);
            properties.setCurrentQuestionId(question.getId());
            if (msg.getPlayerId() == game.getPlayer2().getId())
                properties.setCyclesCount(properties.getCyclesCount() + 1);
            properties.setCurrentDifficulty(node.getDifficulty());

            QuestionMessage message = new QuestionMessage(question);
            if(node.getPlayer() != null){
                message.setGeneral(true);
                properties.setPlayerCorrectAnswer(new HashMap<>());
                stompHeaderAccessor.getSessionAttributes().put("properties", properties);
                simpMessagingTemplate.convertAndSend("/topic/game/" +id, message);
            }
            else{
                stompHeaderAccessor.getSessionAttributes().put("properties", properties);
                simpMessagingTemplate.convertAndSend("/topic/game/" +id, message);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @MessageMapping("/game/{id}/answer")
    public void answer(@DestinationVariable long id, AnswerMessage msg, StompHeaderAccessor stompHeaderAccessor) {
        try {
            Game game = gameService.findById(id);
            GameMap map = (GameMap) stompHeaderAccessor.getSessionAttributes().get("map");
            GameProperties properties = (GameProperties) stompHeaderAccessor.getSessionAttributes().get("properties");
            Player player = (game.getPlayer1().getId() == msg.getPlayerId()) ? game.getPlayer1()
                    : (game.getPlayer2().getId() == msg.getPlayerId()) ? game.getPlayer2()
                    : null;
            if (player == null)
                throw new Exception("Player not found!");

            Node node = map.getNodes().stream()
                    .filter(node1 -> node1.getId() == properties.getCurrentNode().getId())
                    .findFirst()
                    .get();

            boolean answerIsCorrect = questionService.findById(properties.getCurrentQuestionId())
                    .getAnswers().stream()
                    .anyMatch(answer -> answer.isCorrect() && answer.getText().equals(msg.getText()));

            if (properties.getCurrentNode().getPlayer() != null){
                properties.getPlayerCorrectAnswer().put(player.getId(), answerIsCorrect);
                Player defensivePlayer = properties.getCurrentNode().getPlayer();
                Player attackingPlayer = (game.getPlayer1().getId() == defensivePlayer.getId()) ? game.getPlayer2()
                        : game.getPlayer1();

                if (properties.getPlayerCorrectAnswer().keySet().size() == 2) {
                    if (properties.getPlayerCorrectAnswer().get(defensivePlayer.getId())
                            && properties.getPlayerCorrectAnswer().get(attackingPlayer.getId())) {

                        if (properties.getQuestionCount() < 5) {
                            properties.setQuestionCount(properties.getQuestionCount() + 1);
                            properties.setCurrentDifficulty(Math.min(properties.getCurrentDifficulty() + 1, 5));
                            Question question = questionService.findRandomQuestion(game.getTopic(), properties.getCurrentDifficulty());
                            properties.setCurrentQuestionId(question.getId());
                            QuestionMessage message = new QuestionMessage(question);
                            message.setGeneral(true);
                            simpMessagingTemplate.convertAndSend("/topic/game/" + id, message);
                        } else {
                            if( player.getId() == game.getPlayer2().getId()) {
                                properties.setCurrentPlayer(game.getPlayer1());
                            }
                            else
                                properties.setCurrentPlayer(game.getPlayer2());
                            properties.setCurrentQuestionId(-1);
                            properties.setCurrentNode(null);
                            stompHeaderAccessor.getSessionAttributes().put("properties", properties);
                            simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultMessage(defensivePlayer, map));
                        }
                    } else {
                        if (properties.getPlayerCorrectAnswer().get(attackingPlayer.getId())){
                            if(node.isPoint())
                                node.setPlayer(attackingPlayer);
                            else
                                node.setHp(node.getHp() - 1);
                            stompHeaderAccessor.getSessionAttributes().put("map", map);
                            if( player.getId() == game.getPlayer2().getId())
                                properties.setCurrentPlayer(game.getPlayer1());
                            else
                                properties.setCurrentPlayer(game.getPlayer2());
                            properties.setCurrentQuestionId(-1);
                            properties.setCurrentNode(null);
                            stompHeaderAccessor.getSessionAttributes().put("properties", properties);
                            if(node.isBase()){
                                if(node.getHp() < 1) {
                                    Player winner =gameService.finishGame(game, attackingPlayer);
                                    simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultGameMessage(winner, map));
                                }
                                else if(player.getId() == game.getPlayer2().getId() && properties.getCyclesCount() == game.getMaxCycles()){
                                    Player winner =gameService.finishGame(game);
                                    simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultGameMessage(winner, map));
                                }
                                else{
                                    if(game.getPlayer1().getId() == attackingPlayer.getId())
                                        game.setScore1(game.getScore1() + node.getDifficulty());
                                    else
                                        game.setScore2(game.getScore2() + node.getDifficulty());
                                    simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultMessage(attackingPlayer, map));
                                }
                            }
                            else {
                                if(game.getPlayer1().getId() == attackingPlayer.getId())
                                    game.setScore1(game.getScore1() + node.getDifficulty());
                                else
                                    game.setScore2(game.getScore2() + node.getDifficulty());
                                if(player.getId() == game.getPlayer2().getId() && properties.getCyclesCount() == game.getMaxCycles()){
                                    Player winner = gameService.finishGame(game);
                                    simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultGameMessage(winner, map));
                                }
                                else {
                                    simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultMessage(attackingPlayer, map));
                                }
                            }
                        } else {
                            if( player.getId() == game.getPlayer2().getId()) {
                                properties.setCurrentPlayer(game.getPlayer1());
                            }
                            else
                                properties.setCurrentPlayer(game.getPlayer2());
                            properties.setCurrentQuestionId(-1);
                            properties.setCurrentNode(null);
                            stompHeaderAccessor.getSessionAttributes().put("properties", properties);
                            if(player.getId() == game.getPlayer2().getId() && properties.getCyclesCount() == game.getMaxCycles()) {
                                Player winner = gameService.finishGame(game);
                                simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultGameMessage(winner, map));
                            }
                            else
                                simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultMessage(defensivePlayer, map));
                        }
                    }
                }
                else {
                    simpMessagingTemplate.convertAndSend("/topic/game/" +id, new InterimResultMessage(player, answerIsCorrect));
                }
            }else{
                if (answerIsCorrect){
                    if(player.getId() == properties.getCurrentPlayer().getId()) {
                        node.setPlayer(player);
                        stompHeaderAccessor.getSessionAttributes().put("map", map);
                        if( player.getId() == game.getPlayer2().getId())
                            properties.setCurrentPlayer(game.getPlayer1());
                        else
                            properties.setCurrentPlayer(game.getPlayer2());
                        properties.setCurrentQuestionId(-1);
                        properties.setCurrentNode(null);
                        stompHeaderAccessor.getSessionAttributes().put("properties", properties);
                        if(game.getPlayer1().getId() == player.getId())
                            game.setScore1(game.getScore1() + node.getDifficulty());
                        else
                            game.setScore2(game.getScore2() + node.getDifficulty());
                        if(player.getId() == game.getPlayer2().getId() && properties.getCyclesCount() == game.getMaxCycles()){
                            Player winner = gameService.finishGame(game);
                            simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultGameMessage(winner, map));
                        }
                        else
                            simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultMessage(player, map));

                    }
                    else
                        throw new Exception();
                }
                else {
                    if(player.getId() == game.getPlayer2().getId() && properties.getCyclesCount() == game.getMaxCycles()) {
                        Player winner = gameService.finishGame(game);
                        simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultGameMessage(winner, map));
                    }
                    else
                        simpMessagingTemplate.convertAndSend("/topic/game/" + id, new ResultMessage(null, map));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    private GameMap generateGameMap(Game game){
        GameMap map = new GameMap();
        map.addNode(new Node(0, game.getPlayer1()));
        map.addNode(new Node(1, game.getPlayer2()));
        for (int i = 0; i < 3; i++)
            map.addNode(new Node(i + 2, 1));
        for (int i = 0; i < 2; i++)
            map.addNode(new Node(i + 5, 3));
        int[][] pairs = {
                {0, 2},
                {2, 3},
                {3, 4},
                {2, 5},
                {5, 4},
                {2, 6},
                {6, 4},
                {4, 1}
        };
        for (int[] pair : pairs)
            map.addEdge(pair[0], pair[1]);

        return map;
    }
}
