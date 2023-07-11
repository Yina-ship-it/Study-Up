package com.team4822.studyup.controllers.game.dto.in;

public class CaptureMessage {
    private long playerId;
    private int nodeId;

    public CaptureMessage(long playerId, int nodeId) {
        this.playerId = playerId;
        this.nodeId = nodeId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public int getNodeId() {
        return nodeId;
    }
}
