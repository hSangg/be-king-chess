package com.chess.chessbackend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private String game;

    private Integer currentColor;
    private boolean whiteKingSide;
    private boolean whiteQueenSide;
    private boolean blackKingSide;
    private boolean blackQueenSide;
    @Field("pieces")
    private List<Map<String, Object>> pieces;

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Integer currentColor) {
        this.currentColor = currentColor;
    }

    public List<Map<String, Object>> getPieces() {
        return pieces;
    }

    public void setPieces(List<Map<String, Object>> pieces) {
        this.pieces = pieces;
    }
    public boolean isWhiteKingSide() {
        return whiteKingSide;
    }

    public void setWhiteKingSide(boolean whiteKingSide) {
        this.whiteKingSide = whiteKingSide;
    }

    public boolean isWhiteQueenSide() {
        return whiteQueenSide;
    }

    public void setWhiteQueenSide(boolean whiteQueenSide) {
        this.whiteQueenSide = whiteQueenSide;
    }

    public boolean isBlackKingSide() {
        return blackKingSide;
    }

    public void setBlackKingSide(boolean blackKingSide) {
        this.blackKingSide = blackKingSide;
    }

    public boolean isBlackQueenSide() {
        return blackQueenSide;
    }

    public void setBlackQueenSide(boolean blackQueenSide) {
        this.blackQueenSide = blackQueenSide;
    }
}
