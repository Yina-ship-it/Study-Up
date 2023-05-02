package com.team4822.studyup.controllers.quiz;

import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.models.quiz.QType;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.services.quiz.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz/question")
public class QuestionController {
    private final QuestionService questionService;

    @Inject
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions(){
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable long id){
        try{
            Question question = questionService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/attachments")
    public ResponseEntity<?> addAttachment(@PathVariable long id, String url){
        try{
            Question question = questionService.findById(id);
            question.addAttachment(url);
            questionService.saveQuestion(question);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/attachments")
    public ResponseEntity<?> removeAttachment(@PathVariable long id, String url){
        try{
            Question question = questionService.findById(id);
            question.removeAttachment(url);
            questionService.saveQuestion(question);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/text")
    public ResponseEntity<?> changeText(@PathVariable long id, String text){
        try{
            Question question = questionService.findById(id);
            question.setText(text);
            questionService.saveQuestion(question);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/type")
    public ResponseEntity<?> changeType(@PathVariable long id, QType type){
        try{
            Question question = questionService.findById(id);
            if (type == QType.OPEN &&
                    question.getAnswers().stream()
                            .filter(answer -> !answer.isCorrect())
                            .toList().size() > 0)
                throw new Exception("A question with incorrect answers cannot be open!\n(Remove incorrect answers)");
            question.setType(type);
            questionService.saveQuestion(question);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void removeQuestion(@PathVariable long id){
        questionService.deleteQuestion(id);
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<?> createAnswer(String text, boolean correct, @PathVariable long id){
        try{
            Answer answer = questionService.createAnswer(text, correct, id);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
