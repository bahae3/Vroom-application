package com.vroom.vroom.service;

import com.vroom.vroom.model.Chat;
import com.vroom.vroom.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    // List all chat conversations
    public List<Chat> getAllChats() {
        return chatRepository.getAllChats();
    }

    // Create a new chat
    // after a user sends a first message, both sender and receivers will have new chat created
    public int createChat(long idExpediteur, long idDestinaraire) {
        return chatRepository.createChat(idExpediteur, idDestinaraire);
    }

    // Delete a chat
    public int deleteChat(long idChat) {
        return chatRepository.deleteChat(idChat);
    }
}
