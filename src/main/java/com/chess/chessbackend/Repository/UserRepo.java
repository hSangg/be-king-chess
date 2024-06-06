package com.chess.chessbackend.Repository;


import com.chess.chessbackend.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {

}
