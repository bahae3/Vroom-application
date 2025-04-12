package com.vroom.vroom.controller;

import com.vroom.vroom.model.Chat;
import com.vroom.vroom.repository.ChatRepository;
import com.vroom.vroom.repository.MessageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// my idea is to merge message and chat logic together in 1 controller
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    // Retrieve all chat conversations
    @GetMapping("/allChats")
    public ResponseEntity<String> getAllChats() {
        try {
            List<Chat> chats = chatRepository.getAllChats();
            // For simplicity, we return the chats converted to a string.
            return ResponseEntity.ok(chats.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to retrieve chats.");
        }
    }

    // Create a new chat conversation
    // Trigger this endpoint after the first message is sent so both the sender and receiver get a new chat created.
    @PostMapping("/createChat")
    public ResponseEntity<String> createChat(
            @RequestParam long idExpediteur,
            @RequestParam long idDestinataire) {
        try {
            int result = chatRepository.createChat(idExpediteur, idDestinataire);
            if (result > 0) {
                return ResponseEntity.ok("Chat created successfully!");
            } else {
                return ResponseEntity.status(500).body("Failed to create chat.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating chat.");
        }
    }

    // Store a new message
    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(
            @RequestParam long idExpediteur,
            @RequestParam int idDestinataire,
            @RequestParam String contenu) {
        try {
            int result = messageRepository.storeMessage(idExpediteur, idDestinataire, contenu);
            if (result > 0) {
                return ResponseEntity.ok("Message sent successfully!");
            } else {
                return ResponseEntity.status(500).body("Failed to send message.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while sending message.");
        }
    }

    // Update an existing message
    @PutMapping("/updateMessage")
    public ResponseEntity<String> updateMessage(
            @RequestParam long idMessage,
            @RequestParam String contenu) {
        try {
            int result = messageRepository.updateMessage(idMessage, contenu);
            if (result > 0) {
                return ResponseEntity.ok("Message updated successfully!");
            } else {
                return ResponseEntity.status(500).body("Failed to update message.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating message.");
        }
    }

    // Delete a message
    @DeleteMapping("/deleteMessage")
    public ResponseEntity<String> deleteMessage(@RequestParam long idMessage) {
        try {
            int result = messageRepository.deleteMessage(idMessage);
            if (result > 0) {
                return ResponseEntity.ok("Message deleted successfully!");
            } else {
                return ResponseEntity.status(500).body("Failed to delete message.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting message.");
        }
    }

    // Delete a chat conversation
    @DeleteMapping("/deleteChat")
    public ResponseEntity<String> deleteChat(@RequestParam long idChat) {
        try {
            int result = chatRepository.deleteChat(idChat);
            if (result > 0) {
                return ResponseEntity.ok("Chat deleted successfully!");
            } else {
                return ResponseEntity.status(500).body("Failed to delete chat.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting chat.");
        }
    }
}

