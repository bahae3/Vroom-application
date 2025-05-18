package com.vroom.vroom.model;

public class User {
    private Integer idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String motDePasse;
    private byte[] photo;
    private String numDeTele;
    private String roleUser;
    private int isAdmin;

    public User() {
    }

    public User(Integer idUser, String firstName, String lastName, String email, String motDePasse,
                byte[] photo, String numDeTele, String roleUser, int isAdmin) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.motDePasse = motDePasse;
        this.photo = photo;
        this.numDeTele = numDeTele;
        this.roleUser = roleUser;
        this.isAdmin = isAdmin;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getNumDeTele() {
        return numDeTele;
    }

    public void setNumDeTele(String numDeTele) {
        this.numDeTele = numDeTele;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public void setAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

}
