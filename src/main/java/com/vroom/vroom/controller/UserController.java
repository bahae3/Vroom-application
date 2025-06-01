package com.vroom.vroom.controller;

import com.vroom.vroom.dto.LoginDto;
import com.vroom.vroom.dto.RegisterDto;
import com.vroom.vroom.model.User;
import com.vroom.vroom.service.UserService;
import com.vroom.vroom.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        // 1) Hash the incoming password:
        String hashed = passwordEncoder.encode(dto.getMotDePasse());

        // 2) Build a User exactly the same way you did before, via the existing constructor.
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

        boolean created = userService.createUser(u);
        if (!created) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not create " + dto.getRoleUser()));
        }

        User saved = userService.findUserByEmail(u.getEmail());
        return ResponseEntity.ok(
                Map.of(
                        "idUser", saved.getIdUser(),
                        "message", dto.getRoleUser() + " created successfully"
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        User u = userService.loginUser(dto.getEmail(), dto.getMotDePasse());
        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error","Invalid credentials"));
        }
        String role = u.isAdmin() == 1 ? "ADMIN" : u.getRoleUser().toUpperCase();
        String token = jwtUtil.generateToken(u.getEmail(), role);

        return ResponseEntity.ok(
                Map.of(
                        "token",     token,
                        "idUser",    u.getIdUser(),
                        "firstName", u.getFirstName(),
                        "roleUser",  u.getRoleUser()
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> whoAmI(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not logged in"));
        }

        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping(
            value = "/me",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName")  String lastName,
            @RequestPart("email")     String email,
            @RequestPart("numDeTele") String numDeTele,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        // 1) Make sure the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not authenticated"));
        }
        String currentEmail = authentication.getName();
        User existing = userService.findUserByEmail(currentEmail);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not authenticated"));
        }

        // 2) Update just firstName, lastName, email, numDeTele
        existing.setFirstName(firstName);
        existing.setLastName(lastName);
        existing.setEmail(email);
        existing.setNumDeTele(numDeTele);

        // 3) If the client sent a new photo, overwrite the BLOB
        if (photo != null && !photo.isEmpty()) {
            try {
                existing.setPhoto(photo.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to read uploaded photo"));
            }
        }

        // 4) Persist changes
        boolean updated = userService.updateUser(existing);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not save profile"));
        }

        return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
