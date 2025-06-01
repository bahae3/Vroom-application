// File: src/main/java/com/vroom/vroom/controller/PassagerController.java

package com.vroom.vroom.controller;

import com.vroom.vroom.model.*;
import com.vroom.vroom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passager")
@CrossOrigin(origins = "http://localhost:5173")
public class PassagerController {

    @Autowired private UserService userService;
    @Autowired private TrajetsService trajetsService;
    @Autowired private ChatService chatService;
    @Autowired private HistoriqueTrajetService historiqueTrajetService;
    @Autowired private WalletService walletService;
    @Autowired private TransactionService transactionService;
    // Auth helper
    private boolean isPassager(Authentication authentication) {
        User currentUser = userService.findUserByEmail(authentication.getName());
        return currentUser != null && "passager".equalsIgnoreCase(currentUser.getRoleUser());
    }

    @GetMapping("/trajets")
    public ResponseEntity<?> getAllTrajet(Authentication authentication){
        if (!isPassager(authentication) ) {
            return ResponseEntity.status(403).body("Access denied");
        }
        List<Trajets> trajets = trajetsService.getAllTrajets();
        return trajets.isEmpty()
                ? ResponseEntity.status(404).body("Pas encore de trajets")
                : ResponseEntity.ok(trajets);
    }

    @PostMapping("/reserver")
    public ResponseEntity<?> reserveTrajet(
            @RequestParam("trajetId") int trajetId,
            Authentication authentication
    ) {
        // 1) Auth check
        if (!isPassager(authentication)) {
            return ResponseEntity.status(403).body("Access denied");
        }
        User passager = userService.findUserByEmail(authentication.getName());

        // 2) Fetch the Trajet to get its price and conducteur
        Trajets trajet = trajetsService.getTrajetById(trajetId);
        if (trajet == null) {
            return ResponseEntity.status(404).body("Trajet not found");
        }
        double price = trajet.getPrix();

        // 3) Fetch the passenger’s wallet

        Wallet wallet = walletService.findWalletByUser(passager.getIdUser());
        if (wallet == null) {
            return ResponseEntity.status(500).body("Wallet not found for user");
        }

        // 4) Check balance
        if (wallet.getSolde() < price) {
            return ResponseEntity.status(400).body("Solde insuffisant");
        }

        // 5) Deduct price from wallet and update
        double newBalance = wallet.getSolde() - price;
        wallet.setSolde(newBalance);
        int updatedRows = walletService.updateWallet(wallet);
        if (updatedRows <= 0) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour du portefeuille");
        }

        // 6) Record a transaction: type = “Reservation”
        Transaction txn = new Transaction();
        txn.setIdWallet(wallet.getIdWallet());
        txn.setTypeTransaction("Reservation");
        txn.setMontant(price);
        // dateTransaction can default to CURRENT_TIMESTAMP in the DB
        int txnResult = transactionService.createTransaction(txn);
        if (txnResult <= 0) {
            // (Optional) roll back wallet update if you want strict consistency
            return ResponseEntity.status(500).body("Erreur lors de la création de la transaction");
        }

        // 7) Call TrajetsService to reserve the seat
        boolean success = trajetsService.reserverTrajet(passager.getIdUser(), trajetId);
        if (!success) {
            return ResponseEntity.status(400).body("Erreur lors de la réservation du trajet");
        }

        // 8) Create (or re-create) the chat thread between passager and conducteur
        int conducteurId = (int) trajet.getIdConducteur();
        chatService.createChat(passager.getIdUser(), conducteurId);

        return ResponseEntity.ok("Réservation confirmée, solde déduit et conversation créée");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication){
        if (!isPassager(authentication)) {
            return ResponseEntity.status(403).body("Access denied");
        }
        User passager = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(passager);
    }
}
