package com.team4822.studyup.controllers.quiz;

import com.team4822.studyup.models.quiz.QType;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.models.quiz.Topic;
import com.team4822.studyup.services.quiz.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/quiz/topic")
public class TopicController {
    private final TopicService topicService;

    @Inject
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    public List<Topic> getAllTopics(){
        return topicService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTopic(@PathVariable long id){
        try{
            Topic topic = topicService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/title")
    public ResponseEntity<?> changeTitleTopic(String title, @PathVariable long id){
        try{
            Topic topic = topicService.findById(id);
            topic.setTitle(title);
            topicService.saveTopic(topic);
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void removeTopic(@PathVariable long id){
        topicService.deleteTopic(id);
    }

    @PostMapping("/{id}/questions")
    public ResponseEntity<?> createTopic(String text, QType type, @PathVariable long id){
        try{
            Question question = topicService.createQuestion(text, type, id);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
