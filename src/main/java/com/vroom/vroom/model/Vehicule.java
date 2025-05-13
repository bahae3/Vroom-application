package com.vroom.vroom.model;

public class Vehicule {

    private int idVehicule;
    private Long idConducteur;
    private String marque;
    private String typeVehicule;
    private String matricule;
    private String couleur;


    public Vehicule() {}

    public Vehicule(int idVehicule, Long idConducteur, String marque, String typeVehicule, String matricule, String couleur) {
        this.idVehicule = idVehicule;
        this.idConducteur = idConducteur;
        this.marque = marque;
        this.typeVehicule = typeVehicule;
        this.matricule = matricule;
        this.couleur = couleur;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public Long getIdConducteur() {
        return idConducteur;
    }

    public String getMarque() {
        return marque;
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public void setIdConducteur(Long idConducteur) {
        this.idConducteur = idConducteur;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "idVehicule=" + idVehicule +
                ", idConducteur=" + idConducteur +
                ", marque='" + marque + '\'' +
                ", typeVehicule='" + typeVehicule + '\'' +
                ", matricule='" + matricule + '\'' +
                ", couleur='" + couleur + '\'' +
                '}';
    }
}
