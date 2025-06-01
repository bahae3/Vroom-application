package com.vroom.vroom.config;

import com.vroom.vroom.filter.JwtAuthFilter;
import com.vroom.vroom.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private JwtAuthFilter jwtAuthFilter;

    // 1) BCrypt for password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Expose AuthenticationManager so /api/users/login can wire it
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    // 3) Main security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // (A) Enable CORS & disable CSRF (stateless JWT)
                .cors().and()
                .csrf().disable()

                // (B) No session; rely on JWTs only
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // (C) URL‐based authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints: register & login
                        .requestMatchers(HttpMethod.POST, "/api/users/register", "/api/users/login")
                        .permitAll()

                        // Allow any authenticated user to GET/PUT their own profile
                        .requestMatchers(HttpMethod.GET,  "/api/users/me").authenticated()
                        .requestMatchers(HttpMethod.PUT,  "/api/users/me").authenticated()

                        // Trip endpoints (same as before)
                        .requestMatchers(HttpMethod.POST,   "/api/trajet/addTrajet").hasRole("CONDUCTEUR")
                        .requestMatchers(HttpMethod.PUT,    "/api/trajet/updateTrajet").hasRole("CONDUCTEUR")
                        .requestMatchers(HttpMethod.DELETE, "/api/trajet/deleteTrajet").hasRole("CONDUCTEUR")
                        .requestMatchers(HttpMethod.GET,    "/api/trajet/getAllTrajet").authenticated()
                        .requestMatchers(HttpMethod.POST,   "/api/passager/reserver").hasRole("PASSAGER")
                        .requestMatchers(HttpMethod.GET,    "/api/passager/profile").hasAnyRole("PASSAGER", "CONDUCTEUR")
                        .requestMatchers(HttpMethod.GET,    "/api/passager/trajets").hasAnyRole("PASSAGER", "CONDUCTEUR")
                        .requestMatchers(HttpMethod.GET,    "/api/passager/reservations").hasRole("PASSAGER")

                        // Notifications & messaging (any authenticated)
                        .requestMatchers("/api/notification/**").authenticated()
                        .requestMatchers("/api/messages/**").authenticated()
                        .requestMatchers("/api/chat/**").authenticated()

                        // Admin‐only
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                // (D) Insert our custom JWT filter before Spring Security’s UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 4) Global CORS (allow all origins for now)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
