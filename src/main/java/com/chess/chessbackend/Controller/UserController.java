package com.chess.chessbackend.Controller;

import com.chess.chessbackend.Helper.EmailSenderService;
import com.chess.chessbackend.Model.*;
import com.chess.chessbackend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailSenderService emailSenderService;


    @PostMapping("/auth/signUp")
    public ResponseEntity<ApiResponse<User>> signUp(@RequestBody User user) {
        // Check if the email is already registered
        Optional<User> existingUser = Optional.ofNullable(userRepo.findByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("FAILURE", "Email is already registered", null));
        }

        // Validate email format
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("FAILURE", "Invalid email format", null));
        }

        // Additional validation checks
        // For example, password strength check

        // Set created_at and save user
        user.setCreated_at(new Date());
        User savedUser = userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("SUCCESS", "Signed Up", savedUser));
    }

    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<ApiResponse<User>> signIn(@RequestBody RequestLogin requestLogin) {
        // Validate email format
        if (!isValidEmail(requestLogin.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("FAILURE", "Invalid email format", null));
        }

        User user = userRepo.findByEmail(requestLogin.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("FAILURE", "User not found", null));
        }

        // Check if the password matches
        if (user.getPassword().equals(requestLogin.getPassword())) {
            return ResponseEntity.ok()
                    .body(new ApiResponse<>("SUCCESS", "Signed In", user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("FAILURE", "Invalid credentials", null));
        }
    }

    @PostMapping("/user/updateScore")
    public ApiResponse<User> updateScore(@RequestBody UpdateScoreRequest updateScoreRequest) {
        Optional<User> user = userRepo.findById(updateScoreRequest.getUser_id());
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setScore(updateScoreRequest.getScore());
            userRepo.save(existingUser);
            return new ApiResponse<>("SUCCESS", "Score updated successfully", existingUser);
        } else {
            return new ApiResponse<>("FAILURE", "User not found", null);
        }
    }

    @GetMapping("/user/rank")
    public ApiResponse<List<User>> getUserRank() {
        List<User> topUsers = userRepo.findAll()
                .stream()
                .sorted((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore())) // Sort by score in descending order
                .limit(10) // Limit to top 10 users
                .collect(Collectors.toList());

        return new ApiResponse<>("SUCCESS", "Top 10 users by score", topUsers);
    }

    @PostMapping("/user/resetAccount")
    public ApiResponse<String> resetAccount(@RequestBody ResetAccountRequest resetAccountRequest) {

        // Check if the email exists in the database
        Optional<User> optionalUser = Optional.ofNullable(userRepo.findByEmail(resetAccountRequest.getEmail()));
        if (optionalUser.isEmpty()) {
            return new ApiResponse<>("FAILURE", "User with provided email not found", null);
        }

        User user = optionalUser.get();

        // Generate a new random password
        String newPassword = generateRandomPassword();

        // Update the user's password in the database
        user.setPassword(newPassword);
        userRepo.save(user);

        // Send an email containing the new password
        try {
            sendPasswordResetEmail(user.getEmail(), newPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResponse<>("FAILURE", "Failed to send password reset email", null);
        }

        return new ApiResponse<>("SUCCESS", "Password reset successfully. Check your email for the new password.", null);
    }

    public static String generateRandomPassword() {
        Random random = new Random();
        StringBuilder newPassword = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            newPassword.append(random.nextInt(10));
        }
        return newPassword.toString();
    }



    private void sendPasswordResetEmail(String email, String newPassword) {
        String subject = "Chess Game - Password Reset";
        String body = "<html><body>" +
                "<h2>Chess Game Password Reset</h2>" +
                "<p>Your new password is: <strong>" + newPassword + "</strong></p>" +
                "</body></html>";
        emailSenderService.sendEmail(email, subject, body);
    }

}
