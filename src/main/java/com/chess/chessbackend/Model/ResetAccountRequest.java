package com.chess.chessbackend.Model;

public class ResetAccountRequest {
    private String email;

    // Default constructor (required by Spring for deserialization)
    public ResetAccountRequest() {
    }

    // Constructor with email field
    public ResetAccountRequest(String email) {
        this.email = email;
    }

    // Getter and setter for email field
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

