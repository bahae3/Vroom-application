package com.vroom.vroom.service;

import com.vroom.vroom.model.User;
import com.vroom.vroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public boolean createUser(User user) {
        return userRepository.createUser(user) > 0;
    }

    public User loginUser(String email, String rawPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getMotDePasse())) {
            return user;
        }
        return null;
    }

    public boolean deleteUser(Long idUser) {
        return userRepository.deleteUser(idUser) > 0;
    }
}
