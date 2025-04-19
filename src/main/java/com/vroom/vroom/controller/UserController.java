package com.vroom.vroom.controller;

import com.vroom.vroom.model.User;
import com.vroom.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Authorize React.js, to help in frontend dev using api
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get hold of current logged-in user's info
    private User getCurrentUserSession(Authentication authentication) {
        String email = authentication.getName();
        return userService.findUserByEmail(email);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // Create account for Passager and Conducteur
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("numDeTele") String numDeTele,
            @RequestParam("roleUser") String roleUser,
            @RequestParam("photo") MultipartFile photo) throws IOException {
        // Validate photo
        if (photo.getSize() > 8_000_000) { // 8MB max
            return ResponseEntity.badRequest().body("Photo too large");
        }

        // Hash password before storing it
        String hashedPassword = passwordEncoder.encode(motDePasse);
        byte[] photoBytes = photo.getBytes();

        User user = new User(null, firstName, lastName, email, hashedPassword,
                photoBytes, numDeTele, roleUser, 0);

        boolean success = userService.createUser(user);
        return success ?
                ResponseEntity.ok(roleUser + " created successfully") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error creating " + roleUser);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam("email") String email,
            @RequestParam("motDePasse") String motDePasse) {
        User user = userService.loginUser(email, motDePasse);
        if (user == null) {
            // If credentials are invalid, return an error status and message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
        String successMessage;
        if (user.isAdmin() == 1) {
            // Check if the user is Admin, then redirect him to admin interface
            successMessage = "Login successful for admin: " + user.getFirstName();
        } else if ("passager".equals(user.getRoleUser())) {
            // Check if user is Passager, then redirect him to his concerned interface
            successMessage = "Login successful for passager: " + user.getFirstName();
        } else {
            // Check if user is Conducteur, then redirect him to his concerned interface
            successMessage = "Login successful for conducteur: " + user.getFirstName();
        }
        return ResponseEntity.ok(successMessage);
    }
}
