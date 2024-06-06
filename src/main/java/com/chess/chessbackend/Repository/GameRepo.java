package com.chess.chessbackend.Repository;

import com.chess.chessbackend.Model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepo  extends MongoRepository<Game, String>{
}
