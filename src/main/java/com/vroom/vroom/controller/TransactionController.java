package com.vroom.vroom.controller;

import com.vroom.vroom.model.Transaction;
import com.vroom.vroom.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    // Retrieve all transactions
    @GetMapping
    public ResponseEntity<String> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (!transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Transactions retrieved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve transactions.");
        }
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<String> createTransaction(
            @RequestParam("idWallet") long idWallet,
            @RequestParam("typeTransaction") String typeTransaction,
            @RequestParam("montant") double montant) {
        // ps: i made idTransaction=0 here bc we will not need it asslan
        Transaction transaction = new Transaction(0, idWallet, typeTransaction, montant, null);
        int result = transactionService.createTransaction(transaction);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create transaction.");
        }
    }

    // Update an existing transaction
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable("id") long id,
                                                    @RequestParam("typeTransaction") String typeTransaction,
                                                    @RequestParam("montant") double montant) {
        // ps: i made idTransaction=0 & idWallet=0 here bc we will not need them asslan
        Transaction transaction = new Transaction(0, 0, typeTransaction, montant, null);
        int result = transactionService.updateTransaction(transaction);
        if (result > 0) {
            return ResponseEntity.ok("Transaction updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found or update failed.");
        }
    }

    // Delete a transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") long id) {
        int result = transactionService.deleteUser(id);
        if (result > 0) {
            return ResponseEntity.ok("Transaction deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found or delete failed.");
        }
    }
}
