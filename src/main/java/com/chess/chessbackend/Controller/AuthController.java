package com.chess.chessbackend.Controller;

import com.chess.chessbackend.Model.User;
import com.chess.chessbackend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserRepo userRepo;

    @PostMapping("/auth/signUp")
    public void addUser(@RequestParam User user) {
        System.out.println(user);
        userRepo.save(user);
    }
}
