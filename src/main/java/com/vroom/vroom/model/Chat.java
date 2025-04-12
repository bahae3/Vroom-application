package com.vroom.vroom.model;

public class Chat {
    private long idChat, idExpediteur, idDestinataire;

    public Chat() {
    }

    public Chat(long idChat, long idDestinataire, long idExpediteur) {
        this.idChat = idChat;
        this.idDestinataire = idDestinataire;
        this.idExpediteur = idExpediteur;
    }

    public long getIdChat() {
        return idChat;
    }

    public void setIdChat(long idChat) {
        this.idChat = idChat;
    }

    public long getIdDestinataire() {
        return idDestinataire;
    }

    public void setIdDestinataire(long idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public long getIdExpediteur() {
        return idExpediteur;
    }

    public void setIdExpediteur(long idExpediteur) {
        this.idExpediteur = idExpediteur;
    }
}
