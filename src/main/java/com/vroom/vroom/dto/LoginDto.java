// File: src/main/java/com/vroom/vroom/dto/LoginDto.java
package com.vroom.vroom.dto;

public class LoginDto {
    private String email;
    private String motDePasse;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
