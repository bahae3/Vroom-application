package com.vroom.vroom.controller;

import com.vroom.vroom.model.Message;
import com.vroom.vroom.model.User;
import com.vroom.vroom.repository.MessageRepository;
import com.vroom.vroom.service.MessageService;
import com.vroom.vroom.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;


    //Authentification
    private boolean isMessageSender(Authentication authentication) {
        User user= userService.findUserByEmail(authentication.getName());
        return user != null && ("passager".equalsIgnoreCase(user.getRoleUser()) || "conducteur".equalsIgnoreCase(user.getRoleUser()));

    }
    // recuperer tout les messages
    @GetMapping("/allMessages")
    public ResponseEntity<?> getAllMessages(Authentication authentication) {
        List<Message> messages = messageService.getAllMessages();
        if(messages.isEmpty()){
            return ResponseEntity.status(404).body("la liste des messages est vide");
        }
        else {
            return ResponseEntity.ok(messages);
        }

    }

    // pour envoyer un message
    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestParam long idDestinataire,@RequestParam String contenu, Authentication authentication) {
        User userSender= userService.findUserByEmail(authentication.getName());
        if (userSender == null) return ResponseEntity.status(403).body("erreur d'authentification");

        int result = messageService.storeMessage(userSender.getIdUser(), (int) idDestinataire,contenu);
        return result > 0 ? ResponseEntity.ok("message envoyé avec succes") : ResponseEntity.status(500).body("erreur lors de l'envoi du message");

    }

    //pour recevoir un message par un destinataire
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedMessages(Authentication authentication) {
        User receiver = userService.findUserByEmail(authentication.getName());
        if (receiver == null) return ResponseEntity.status(403).body("Erreur d'authentification");

        List<Message> messages = messageService.findMessagesByDestinataire(receiver.getIdUser());
        return messages.isEmpty()
                ? ResponseEntity.status(404).body("Aucun message reçu")
                : ResponseEntity.ok(messages);
    }

    //Pour supprimer un message
    @DeleteMapping("/{idMessage}")
    public ResponseEntity<?> deleteMessage(@RequestParam long idMessage, Authentication authentication) {
        User sender = userService.findUserByEmail(authentication.getName());
        if (sender == null) return ResponseEntity.status(403).body("Erreur d'authentification");

        int result = messageService.deleteMessage(idMessage);
        return result>0 ? ResponseEntity.ok("Suppression de message avec succes") : ResponseEntity.status(500).body("erreur lors de la suppression du message");

    }

    //Pour modifier un message
    @PutMapping("/updateMessage")
    public ResponseEntity<?> updateMessage(@RequestParam long idMessage,@RequestParam String contenu, Authentication authentication) {
        User sender = userService.findUserByEmail(authentication.getName());
        if (sender == null) return ResponseEntity.status(403).body("Erreur d'authentification");

       Message message = messageService.findMessageById(idMessage);

       if(message == null) return ResponseEntity.status(404).body("pas de message pour le moment");
       if (message.getIdExpediteur() != sender.getIdUser()) return ResponseEntity.status(403).body("Vous ne pouvez pas modifier ce message");

       int result = messageService.updateMessage(idMessage,contenu);
       return result>0 ? ResponseEntity.ok("Modification de message avec succes") : ResponseEntity.status(500).body("erreur lors de la modification du message");
    }

}
