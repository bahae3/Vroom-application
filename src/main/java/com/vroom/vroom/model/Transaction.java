package com.vroom.vroom.model;

import java.sql.Date;

public class Transaction {
    private long idTransaction;
    private long idWallet;
    private String typeTransaction;
    private double montant;
    private Date dateTransaction;

    public Transaction() {
    }

    public Transaction(long idTransaction, long idWallet, String typeTransaction, double montant, Date dateTransaction) {
        this.idTransaction = idTransaction;
        this.idWallet = idWallet;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.dateTransaction = dateTransaction;
    }

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public long getIdWallet() {
        return idWallet;
    }

    public void setIdWallet(long idWallet) {
        this.idWallet = idWallet;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}