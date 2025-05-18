package com.vroom.vroom.controller;

import com.vroom.vroom.model.Transaction;
import com.vroom.vroom.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /** GET /api/transactions */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> txns = transactionService.getAllTransactions();
        return ResponseEntity.ok(txns);
    }

    /** POST /api/transactions?idWallet=…&typeTransaction=…&montant=… */
    @PostMapping
    public ResponseEntity<String> createTransaction(
            @RequestParam("idWallet") long idWallet,
            @RequestParam("typeTransaction") String typeTransaction,
            @RequestParam("montant") double montant) {

        Transaction t = new Transaction(0, idWallet, typeTransaction, montant, null);
        int result = transactionService.createTransaction(t);
        if (result > 0) {
            return ResponseEntity.status(201).body("Transaction created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create transaction.");
        }
    }

    /** PUT /api/transactions/{id}?typeTransaction=…&montant=… */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransaction(
            @PathVariable("id") long id,
            @RequestParam("typeTransaction") String typeTransaction,
            @RequestParam("montant") double montant) {

        Transaction t = new Transaction(id, 0, typeTransaction, montant, null);
        int result = transactionService.updateTransaction(t);
        if (result > 0) {
            return ResponseEntity.ok("Transaction updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/transactions/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") long id) {
        int result = transactionService.deleteTransaction(id);
        if (result > 0) {
            return ResponseEntity.ok("Transaction deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
