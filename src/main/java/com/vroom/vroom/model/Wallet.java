package com.vroom.vroom.model;

public class Wallet {
    private long idWallet;
    private long idUser;
    private double solde;

    public Wallet() {
    }

    public Wallet(long idWallet, long idUser, double solde) {
        this.idWallet = idWallet;
        this.idUser = idUser;
        this.solde = solde;
    }

    public long getIdWallet() {
        return idWallet;
    }

    public void setIdWallet(long idWallet) {
        this.idWallet = idWallet;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
