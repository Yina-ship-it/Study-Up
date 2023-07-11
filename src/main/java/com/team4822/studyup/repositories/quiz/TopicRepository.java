package com.team4822.studyup.repositories.quiz;

import com.team4822.studyup.models.quiz.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
