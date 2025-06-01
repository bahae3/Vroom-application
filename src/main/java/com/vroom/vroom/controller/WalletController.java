package com.vroom.vroom.controller;

import com.vroom.vroom.model.Wallet;
import com.vroom.vroom.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    // Retrieves all wallets
    @GetMapping
    public ResponseEntity<List<Wallet>> getAllWallets() {
        List<Wallet> wallets = walletService.getAllWallets();
        return ResponseEntity.ok(wallets);
    }

    // Creates a new wallet for a given user ID
    @PostMapping
    public ResponseEntity<String> createWallet(@RequestParam long idUser) {
        int result = walletService.createWallet(idUser);
        if (result > 0) {
            return ResponseEntity.status(201).body("Wallet created successfully");
        } else {
            return ResponseEntity.status(500).body("Error creating wallet");
        }
    }

    // Updates the wallet, typically after a transaction.
    @PutMapping("/{idWallet}")
    public ResponseEntity<String> updateWallet(
            @PathVariable long idWallet,
            @RequestBody Wallet wallet) {
        wallet.setIdWallet(idWallet);
        int result = walletService.updateWallet(wallet);
        if (result > 0) {
            return ResponseEntity.ok("Wallet updated successfully");
        } else {
            return ResponseEntity.status(404).body("Wallet not found");
        }
    }

    // Delete a wallet
    @DeleteMapping("/{idWallet}")
    public ResponseEntity<String> deleteWallet(@PathVariable long idWallet) {
        int result = walletService.deleteWallet(idWallet);
        if (result > 0) {
            return ResponseEntity.ok("Wallet deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Wallet not found");
        }
    }

    // Find a wallet by user id. If not found, return a default wallet with solde = 0.0
    @GetMapping("/user/{idUser}")
    public ResponseEntity<Wallet> getWalletByUser(@PathVariable long idUser) {
        Wallet wallet = walletService.findWalletByUser(idUser);
        if (wallet != null) {
            return ResponseEntity.ok(wallet);
        } else {
            Wallet defaultWallet = new Wallet();
            defaultWallet.setIdWallet(0L);
            defaultWallet.setIdUser(idUser);
            defaultWallet.setSolde(0.0);
            return ResponseEntity.ok(defaultWallet);
        }
    }
}
