package com.team4822.studyup.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String title;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "subject")
    private List<Topic> topics;

    public Subject(String title) {
        this.title = title;
        topics = new ArrayList<>();
    }

    public Subject() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
