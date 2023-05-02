package com.team4822.studyup.services.quiz;

import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.models.quiz.QType;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.repositories.quiz.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Inject
    public QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

    public Question findById(long id) throws Exception {
        Optional<Question> question = questionRepository.findById(id);
        if(question.isEmpty())
            throw new Exception("Question not found!");
        return question.get();
    }

    public void saveQuestion(Question question){
        questionRepository.save(question);
    }

    public void deleteQuestion(long id){
        questionRepository.deleteById(id);
    }

    public Answer createAnswer(String text, boolean correct, long questionId) throws Exception {
        Question question = findById(questionId);
        if (!correct && question.getType() == QType.OPEN)
            throw new Exception("You do not need to create incorrect answers for an open question!");
        else if (correct && question.getType() == QType.CLOSE)
            question.getAnswers()
                    .forEach(answer -> {
                        answer.setCorrect(false);
                        answerService.saveAnswer(answer);
                    });
        Answer answer = new Answer(text, correct, question);
        answerService.saveAnswer(answer);
        return answer;
    }
}
