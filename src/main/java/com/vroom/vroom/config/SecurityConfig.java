package com.vroom.vroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for development and testing (like my case in postman testing)
                .csrf(csrf -> csrf.disable())
                // Configure endpoint authorization.
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to all requests that start with /api/users/
                        .requestMatchers("/api/trajet/**","/api/trajet/getAllTrajet").permitAll()
                        // Require authentication for all other requests.
                        .anyRequest().authenticated())
                // Enable HTTP Basic authentication
                .httpBasic(withDefaults());

        return http.build();
    }

    // This is used for hashing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}