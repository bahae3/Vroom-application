package com.vroom.vroom.model;

import java.sql.Date;

public class Message {
    private long idMessage;
    private long idExpediteur;
    private long idDestinataire;
    private String contenu;
    private Date dateEnvoi;

    public Message() {
    }

    public Message(long idMessage, long idExpediteur, long idDestinataire, String contenu, Date dateEnvoi) {
        this.idMessage = idMessage;
        this.idExpediteur = idExpediteur;
        this.idDestinataire = idDestinataire;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
    }

    public long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(long idMessage) {
        this.idMessage = idMessage;
    }

    public long getIdExpediteur() {
        return idExpediteur;
    }

    public void setIdExpediteur(long idExpediteur) {
        this.idExpediteur = idExpediteur;
    }

    public long getIdDestinataire() {
        return idDestinataire;
    }

    public void setIdDestinataire(long idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}
