package com.vroom.vroom.service;

import com.vroom.vroom.model.User;
import com.vroom.vroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public boolean createUser(User user) {
        return userRepository.createUser(user) > 0;
    }

    public boolean deleteUser(Long idUser) {
        return userRepository.deleteUser(idUser) > 0;
    }
}
