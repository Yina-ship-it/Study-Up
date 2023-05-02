package com.team4822.studyup.controllers.quiz;

import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.models.quiz.QType;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.services.quiz.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/quiz/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Inject
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/all")
    public List<Answer> getAllAnswers(){
        return answerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnswer(@PathVariable long id){
        try{
            Answer answer = answerService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/text")
    public ResponseEntity<?> changeText(@PathVariable long id, String text){
        try{
            Answer answer = answerService.findById(id);
            answer.setText(text);
            answerService.saveAnswer(answer);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/correct")
    public ResponseEntity<?> changeText(@PathVariable long id, boolean correct){
        try{
            Answer answer = answerService.findById(id);
            if(answer.getQuestion().getType() == QType.OPEN){
                if(!correct)
                    throw new Exception("You do not need to create incorrect answers for an open question!");
            }else {
                if (correct)
                    answer.getQuestion().getAnswers()
                            .forEach(a -> {
                                a.setCorrect(false);
                                answerService.saveAnswer(a);
                            });
            }
            answer.setCorrect(correct);
            answerService.saveAnswer(answer);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void removeAnswer(@PathVariable long id){
        answerService.deleteAnswer(id);
    }
}
