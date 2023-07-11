package com.team4822.studyup.services.quiz;

import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.repositories.quiz.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Inject
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findAll(){
        return answerRepository.findAll();
    }

    public Answer findById(long id) throws Exception {
        Optional<Answer> answer = answerRepository.findById(id);
        if(answer.isEmpty())
            throw new Exception("Answer not found!");
        return answer.get();
    }

    public void saveAnswer(Answer answer){
        answerRepository.save(answer);
    }

    public void deleteAnswer(long id){
        answerRepository.deleteById(id);
    }
}