package com.chess.chessbackend.Model;

public class UpdateScoreRequest {
    private String user_id;
    private Integer score;

    // Constructors, getters, and setters

    public UpdateScoreRequest() {}

    public UpdateScoreRequest(String user_id, Integer score) {
        this.user_id = user_id;
        this.score = score;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
