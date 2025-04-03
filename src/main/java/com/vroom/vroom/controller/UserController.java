package com.vroom.vroom.controller;

import com.vroom.vroom.model.User;
import com.vroom.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Autoriser React.js, to help in frontend dev using api
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // Create account for Passager
    @PostMapping("/addPassager")
    public String addPassager(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("numDeTele") String numDeTele,
            @RequestParam("photo") MultipartFile photo) throws IOException {

        // Hashing the password for more security
        String hashedPassword = passwordEncoder.encode(motDePasse);
        // Storing a photo using a photo type
        byte[] photoBytes = photo.getBytes();

        User user = new User(null, firstName, lastName, email, hashedPassword, photoBytes, numDeTele, "passager", 0);
        boolean success = userService.createUser(user);
        return success ? "Passager created successfully" : "Error creating passager";
    }

    // Create account for Conducteur
    @PostMapping("/addConducteur")
    public String addConducteur(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("numDeTele") String numDeTele,
            @RequestParam("photo") MultipartFile photo) throws IOException {

        // Hashing the password for more security
        String hashedPassword = passwordEncoder.encode(motDePasse);
        // Storing a photo using a photo type
        byte[] photoBytes = photo.getBytes();

        User user = new User(null, firstName, lastName, email, hashedPassword, photoBytes, numDeTele, "conducteur", 0);
        boolean success = userService.createUser(user);
        return success ? "Conducteur created successfully" : "Error creating conducteur";
    }

    // Login endpoint
    @PostMapping("/login")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("motDePasse") String motDePasse) {
        User user = userService.loginUser(email, motDePasse);
        if (user != null) {
            return "Login successful for user: " + user.getEmail();
        }
        return "Invalid email or password";
    }

    // To delete a user by an admin
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return success ? "User deleted successfully" : "Error deleting user";
    }
}
