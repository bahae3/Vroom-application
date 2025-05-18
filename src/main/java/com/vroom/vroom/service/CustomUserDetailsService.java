// src/main/java/com/vroom/vroom/service/CustomUserDetailsService.java
package com.vroom.vroom.service;

import com.vroom.vroom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userService.findUserByEmail(email);
        if (u == null) throw new UsernameNotFoundException("No user " + email);

        // Trim and uppercase the roleUser
        String raw = u.getRoleUser() == null ? "" : u.getRoleUser().trim();
        String role = u.isAdmin() == 1
                ? "ROLE_ADMIN"
                : "ROLE_" + raw.toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getMotDePasse(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
