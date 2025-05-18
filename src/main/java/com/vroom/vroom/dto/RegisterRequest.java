// File: src/main/java/com/vroom/vroom/dto/RegisterRequest.java
package com.vroom.vroom.dto;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String motDePasse;
    private String numDeTele;
    private String roleUser;

    // getters & setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getNumDeTele() { return numDeTele; }
    public void setNumDeTele(String numDeTele) { this.numDeTele = numDeTele; }
    public String getRoleUser() { return roleUser; }
    public void setRoleUser(String roleUser) { this.roleUser = roleUser; }
}
