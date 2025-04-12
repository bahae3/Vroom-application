package com.vroom.vroom.model;

import java.sql.Date;

public class Notification {
    private long idNotification;
    private long idUser;
    private String titre;
    private String message;
    private Date dateNotification;

    public Notification() {
    }

    public Notification(long idNotification, long idUser, String titre, String message, Date dateNotification) {
        this.idNotification = idNotification;
        this.idUser = idUser;
        this.titre = titre;
        this.message = message;
        this.dateNotification = dateNotification;
    }

    public long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(long idNotification) {
        this.idNotification = idNotification;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }
}
