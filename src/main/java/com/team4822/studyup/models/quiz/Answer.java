package com.team4822.studyup.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue
    private long id;

    private String text;
    private boolean correct;

    @JsonIgnoreProperties({ "text" , "attachments" , "type" , "topic" , "answers" })
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(String text, boolean correct, Question question) {
        this.text = text;
        this.correct = correct;
        this.question = question;
    }

    public Answer() {

    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Question getQuestion() {
        return question;
    }
}
