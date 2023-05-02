package com.team4822.studyup.repositories.quiz;

import com.team4822.studyup.models.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
