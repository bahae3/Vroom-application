package com.vroom.vroom.controller;

import com.vroom.vroom.dto.LoginDto;
import com.vroom.vroom.dto.RegisterDto;
import com.vroom.vroom.model.User;
import com.vroom.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.vroom.vroom.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")   // your React origin
public class UserController {

    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        String hashed = passwordEncoder.encode(dto.getMotDePasse());
        User u = new User(
                null,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                hashed,
                new byte[0],
                dto.getNumDeTele(),
                dto.getRoleUser(),
                0
        );
        if (!userService.createUser(u)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not create " + dto.getRoleUser()));
        }
        User saved = userService.findUserByEmail(u.getEmail());
        return ResponseEntity.ok(Map.of(
                "idUser", saved.getIdUser(),
                "message", dto.getRoleUser() + " created successfully"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        User u = userService.loginUser(dto.getEmail(), dto.getMotDePasse());
        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error","Invalid credentials"));
        }
        String role = u.isAdmin()==1 ? "ADMIN" : u.getRoleUser().toUpperCase();
        String token = jwtUtil.generateToken(u.getEmail(), role);
        return ResponseEntity.ok(Map.of(
                "token",     token,
                "idUser",    u.getIdUser(),
                "firstName", u.getFirstName(),
                "roleUser",  u.getRoleUser()
        ));
    }


// === WHO AM I? ===
    @GetMapping("/me")
    public ResponseEntity<?> whoAmI() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not logged in"));
        }
        String email = auth.getName();
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // === (optional) ADMIN: list all users ===
    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
