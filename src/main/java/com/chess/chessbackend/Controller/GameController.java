package com.chess.chessbackend.Controller;

import com.chess.chessbackend.Model.Game;
import com.chess.chessbackend.Repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameRepo gameRepository;

    @GetMapping("/load")
    @ResponseBody
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        System.out.println(id);
        Optional<Game> game = gameRepository.findById(id); // Sử dụng String thay vì Long
        if (game.isPresent()) {
            return ResponseEntity.ok(game.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> createGame(@RequestBody Game game) {
        try {
            Game savedGame = gameRepository.save(game);

            return new ResponseEntity<>(new ApiResponse(201, "Game saved", savedGame), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(400, "An error occurred", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/override/{id}")
    @ResponseBody
    public ResponseEntity<?> updateGame(@PathVariable String id, @RequestBody Game gameDetails) {
        try {
            Optional<Game> optionalGame = gameRepository.findById(id);
            if (!optionalGame.isPresent()) {
                return new ResponseEntity<>(new ApiResponse(404, "Game not found", null), HttpStatus.NOT_FOUND);
            }
            System.out.println(gameDetails.getCurrentColor());
            Game game = optionalGame.get();
            game.setPieces(gameDetails.getPieces());
            game.setWhiteKingSide(gameDetails.isWhiteKingSide());
            game.setWhiteQueenSide(gameDetails.isWhiteQueenSide());
            game.setBlackKingSide(gameDetails.isBlackKingSide());
            game.setBlackQueenSide(gameDetails.isBlackQueenSide());
            game.setCurrentColor(gameDetails.getCurrentColor());
            gameRepository.save(game);

            return new ResponseEntity<>(new ApiResponse(200, "Game updated successfully", game), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(400, "An error occurred", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteGame(@PathVariable String id) {
        gameRepository.deleteById(id);
    }

    // Lớp ApiResponse để định dạng phản hồi
    static class ApiResponse {
        private int status;
        private String message;
        private Object data;

        public ApiResponse(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters và Setters
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
