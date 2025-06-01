package com.vroom.vroom.model;

import java.time.LocalDateTime;


public class Trajets {
    private int idTrajet;
    private long idConducteur;
    private String pointDepart;
    private String pointArrivee;
    private LocalDateTime heureDepart;
    private int placesDisponibles;
    private float prix;
    private String etat;


    public int getIdTrajet() {
        return idTrajet;
    }

    public long getIdConducteur() {
        return idConducteur;
    }

    public String getPointDepart() {
        return pointDepart;
    }

    public String getPointArrivee() {
        return pointArrivee;
    }

    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }

    public int getPlacesDisponibles() {
        return placesDisponibles;
    }

    public float getPrix() {
        return prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }

    public void setIdConducteur(long idConducteur) {
        this.idConducteur = idConducteur;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public void setPointArrivee(String pointArrivee) {
        this.pointArrivee = pointArrivee;
    }

    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public void setPlacesDisponibles(int placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Trajets{" +
                "idTrajet=" + idTrajet +
                ", idConducteur=" + idConducteur +
                ", pointDepart='" + pointDepart + '\'' +
                ", pointArrivee='" + pointArrivee + '\'' +
                ", heureDepart=" + heureDepart +
                ", placesDisponibles=" + placesDisponibles +
                ", prix=" + prix +
                ", etat='" + etat + '\'' +
                '}';
    }


}
