package com.vroom.vroom.controller;

import com.vroom.vroom.model.Transaction;
import com.vroom.vroom.model.User;
import com.vroom.vroom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private TrajetsService trajetService;
    @Autowired private MessageService messageService;
    @Autowired private WalletService walletService;
    @Autowired private NotificationService notificationService;
    @Autowired private TransactionService transactionService;

    private boolean isAdmin(Authentication authentication) {
        User currentUser = userService.findUserByEmail(authentication.getName());
        return currentUser != null && currentUser.isAdmin() == 1;
    }

    // ✅ Tous les utilisateurs
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Supprimer un utilisateur
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        return userService.deleteUser(id) ?
                ResponseEntity.ok("User deleted") :
                ResponseEntity.status(404).body("User not found");
    }

    // ✅ Promouvoir un utilisateur
    @PatchMapping("/users/{id}/promote")
    public ResponseEntity<?> promoteUser(@PathVariable Long id, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        User user = userService.findUserById(id);
        if (user == null) return ResponseEntity.status(404).body("User not found");
        user.setAdmin(1);
        return userService.updateUser(user) ?
                ResponseEntity.ok("User promoted to admin") :
                ResponseEntity.status(500).body("Error");
    }

    // ✅ Voir tous les trajets
    @GetMapping("/trajets")
    public ResponseEntity<?> getAllTrajets(Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        return ResponseEntity.ok(trajetService.getAllTrajets());
    }

   // ✅ Supprimer un trajet
   @DeleteMapping("/trajets/{id}")
   public ResponseEntity<?> deleteTrajet(
           @PathVariable("id") int trajetId,
           Authentication authentication
   ) {
       if (!isAdmin(authentication)) {
           return ResponseEntity.status(403).body("Access denied");
       }

       boolean deleted = trajetService.deleteTrajetAdmin(trajetId);
       if (deleted) {
           return ResponseEntity.ok("Trajet deleted");
       } else {
           return ResponseEntity.status(404).body("Trajet not found");
       }
   }

    // ✅ Lire les messages
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages(Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // ✅ Lire le wallet d'un utilisateur
    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getUserWallet(@PathVariable int userId, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        return ResponseEntity.ok(walletService.findWalletByUser(userId));
    }

    // ✅ Envoyer une notification
    @PostMapping("/notify")
    public ResponseEntity<?> sendNotification(@RequestParam Long idUser,
                                              @RequestParam String titre,
                                              @RequestParam String message,
                                              Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(403).body("Access denied");
        boolean sent = notificationService.sendNotification(idUser, titre, message);
        return sent ? ResponseEntity.ok("Notification sent") : ResponseEntity.status(500).body("Failed to send");
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions(Authentication authentication) {
        if (!isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Access denied"));
        }
        List<Transaction> txns = transactionService.getAllTransactions();
        return ResponseEntity.ok(txns);
    }

}
