package com.vroom.vroom.model;

public class HistoriqueTrajet {
    private long idHistorique, idUser, idTrajet;

    public HistoriqueTrajet() {
    }

    public HistoriqueTrajet(long idHistorique, long idUser, long idTrajet) {
        this.idHistorique = idHistorique;
        this.idUser = idUser;
        this.idTrajet = idTrajet;
    }

    public long getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(long idHistorique) {
        this.idHistorique = idHistorique;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(long idTrajet) {
        this.idTrajet = idTrajet;
    }
}
