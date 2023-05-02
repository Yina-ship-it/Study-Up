package com.team4822.studyup.services.quiz;

import com.team4822.studyup.models.quiz.Subject;
import com.team4822.studyup.models.quiz.Topic;
import com.team4822.studyup.repositories.quiz.SubjectRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final TopicService topicService;

    @Inject
    public SubjectService(SubjectRepository subjectRepository, TopicService topicService) {
        this.subjectRepository = subjectRepository;
        this.topicService = topicService;
    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

    public Subject findById(long id) throws Exception {
        Optional<Subject> subject = subjectRepository.findById(id);
        if(subject.isEmpty())
            throw new Exception("Subject not found!");
        return subject.get();
    }

    public void saveSubject(Subject subject){
        subjectRepository.save(subject);
    }

    public void deleteSubject(long id){
        subjectRepository.deleteById(id);
    }

    public Topic createTopic(String title, long subjectId) throws Exception {
        Subject subject = findById(subjectId);
        Topic topic = new Topic(title, subject);
        topicService.saveTopic(topic);
        return topic;
    }
}
