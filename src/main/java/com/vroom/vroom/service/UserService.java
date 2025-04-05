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

    // List all users in db service (for admin)
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // Create user account service
    public boolean createUser(User user) {
        return userRepository.createUser(user) > 0;
    }

    // findUserByEmail() method will be used to get hold of the user when logged in
    // like if I want to get hold of the current logged-in user's id
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    // findUserById() method used in update user info
    public User findUserById(long idUser){
        return userRepository.findUserById(idUser);
    }

    // findUserByEmail() method will be used here for login
    public User loginUser(String email, String rawPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getMotDePasse())) {
            return user;
        }
        return null;
    }

    // Delete user service
    public boolean deleteUser(Long idUser) {
        return userRepository.deleteUser(idUser) > 0;
    }

    // Update user service
    public boolean updateUser(User user) {
        return userRepository.updateUser(user) > 0;
    }
}
