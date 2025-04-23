package com.vroom.vroom.service;

import com.vroom.vroom.model.Message;
import com.vroom.vroom.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    // Store a message after it's sent
    public int storeMessage(long idSender, int idReceiver, String content){
        return messageRepository.storeMessage(idSender, idReceiver, content);
    }

    // If a user wished to modify a message
    public int updateMessage(long idMessage, String contenu){
        return messageRepository.updateMessage(idMessage, contenu);
    }

    // If a user wishes to delete a message
    public int deleteMessage(long idMessage) {
        return messageRepository.deleteMessage(idMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAllMessages();
    }

    //service pour recuperer les messages par un destinataire
    public List<Message> findMessagesByDestinataire(long idDestinataire) {
        return messageRepository.findMessagesByDestinataire(idDestinataire);
    }

    //service pour recuperer un message par son id
    public Message findMessageById(long idMessage) {
        return messageRepository.findMessageById(idMessage);
    }


}
