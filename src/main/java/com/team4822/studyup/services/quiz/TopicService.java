package com.team4822.studyup.services.quiz;

import com.team4822.studyup.models.quiz.QType;
import com.team4822.studyup.models.quiz.Question;
import com.team4822.studyup.models.quiz.Subject;
import com.team4822.studyup.models.quiz.Topic;
import com.team4822.studyup.repositories.quiz.TopicRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final QuestionService questionService;

    @Inject
    public TopicService(TopicRepository topicRepository, QuestionService questionService) {
        this.topicRepository = topicRepository;
        this.questionService = questionService;
    }

    public List<Topic> findAll(){
        return topicRepository.findAll();
    }

    public Topic findById(long id) throws Exception {
        Optional<Topic> topic = topicRepository.findById(id);
        if(topic.isEmpty())
            throw new Exception("Topic not found!");
        return topic.get();
    }

    public void saveTopic(Topic topic){
        topicRepository.save(topic);
    }

    public void deleteTopic(long id){
        topicRepository.deleteById(id);
    }

    public Question createQuestion(String text, QType type, long topicId) throws Exception {
        Topic topic = findById(topicId);
        Question question = new Question(text,type,topic);
        questionService.saveQuestion(question);
        return question;
    }
}
