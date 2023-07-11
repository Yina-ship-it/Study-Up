package com.team4822.studyup.controllers.game.dto.out;

public abstract class Message {
    protected MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public enum MessageType {
        MAP("MAP"), QUESTION("QUESTION"), INTERIM("INTERIM"), CAPTURE_RESULT("CAPTURE_RESULT"), GAME_RESULT("GAME_RESULT");
    
        MessageType(String type) {
        }
    }
}
