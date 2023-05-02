package com.team4822.studyup.controllers.quiz;

import com.team4822.studyup.models.quiz.Subject;
import com.team4822.studyup.models.quiz.Topic;
import com.team4822.studyup.services.quiz.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/quiz/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @Inject
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    public List<Subject> getAllSubjects(){
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubject(@PathVariable long id){
        try{
            Subject subject = subjectService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createSubject(String title){
        try{
            Subject subject = new Subject(title);
            subjectService.saveSubject(subject);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/title")
    public ResponseEntity<?> changeTitleSubject(String title, @PathVariable long id){
        try{
            Subject subject = subjectService.findById(id);
            subject.setTitle(title);
            subjectService.saveSubject(subject);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<?> changeDescriptionSubject(String description, @PathVariable long id){
        try{
            Subject subject = subjectService.findById(id);
            subject.setDescription(description);
            subjectService.saveSubject(subject);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void removeSubject(@PathVariable long id){
        subjectService.deleteSubject(id);
    }

    @PostMapping("/{id}/topics")
    public ResponseEntity<?> createTopic(String title, @PathVariable long id){
        try{
            Topic topic = subjectService.createTopic(title, id);
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
