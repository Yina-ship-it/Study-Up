package com.team4822.studyup.controllers.game.dto.out;

import com.team4822.studyup.models.quiz.Answer;
import com.team4822.studyup.models.quiz.QType;
import com.team4822.studyup.models.quiz.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestionMessage extends Message {
    private String text;
    private Set<String> attachments;
    private QType type;
    private List<String> answers;

    private boolean isGeneral;

    public QuestionMessage(Question question) {
        super(MessageType.QUESTION);
        this.text = question.getText();
        this.attachments = question.getAttachments();
        this.type = question.getType();
        this.answers = new ArrayList<>();
        if(type == QType.CLOSE){
            for (Answer answer: question.getAnswers()) {
                this.answers.add(answer.getText());
            }
        }
        this.isGeneral = false;
    }

    public void setGeneral(boolean general) {
        isGeneral = general;
    }

    public String getText() {
        return text;
    }

    public Set<String> getAttachments() {
        return attachments;
    }

    public QType getType() {
        return type;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isGeneral() {
        return isGeneral;
    }

}
