package com.chess.chessbackend.Controller;

import com.chess.chessbackend.Model.RequestLogin;
import com.chess.chessbackend.Model.User;
import com.chess.chessbackend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {
    @Autowired
    UserRepo userRepo;


    @PostMapping("/auth/signUp")
    public User signUp(@RequestBody User user) {
        user.setCreated_at(new Date());
        return userRepo.save(user);
    }

    @PostMapping("/auth/signIn")
    public User signIn(@RequestBody RequestLogin requestLogin) {
        User user = userRepo.findByEmail(requestLogin.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the password matches
        if (user.getPassword().equals(requestLogin.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
