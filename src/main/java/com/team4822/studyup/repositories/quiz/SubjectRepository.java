package com.team4822.studyup.repositories.quiz;

import com.team4822.studyup.models.quiz.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
