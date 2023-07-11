package com.team4822.studyup.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private long id;

    private String text;

    private int difficulty;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="attachments", joinColumns=@JoinColumn(name="question_id"))
    @Column(name="url")
    private Set<String> attachments;

    @Enumerated(EnumType.STRING)
    private QType type;

    @JsonIgnoreProperties({ "questions" , "subject" })
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;

    public Question(String questionText, int difficulty, QType type, Topic topic) {
        this.text = questionText;
        this.attachments = new HashSet<>();
        this.type = type;
        this.topic = topic;
        answers = new ArrayList<>();

        this.difficulty = difficulty;
    }

    public Question() {

    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Set<String> getAttachments() {
        return attachments;
    }

    public void addAttachment(String attachmentUrl) {
        this.attachments.add(attachmentUrl);
    }

    public void removeAttachment(String attachmentUrl) {
        attachments.remove(attachmentUrl);
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QType getType() {
        return type;
    }

    public void setType(QType type) {
        this.type = type;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}
