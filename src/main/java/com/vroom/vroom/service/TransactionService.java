package com.vroom.vroom.service;

import com.vroom.vroom.model.Transaction;
import com.vroom.vroom.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    // List all transactions in db
    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    // Create a new transaction
    public int createTransaction(Transaction tr) {
        return transactionRepository.createTransaction(tr);
    }

    // Update a transaction
    public int updateTransaction(Transaction tr) {
        return transactionRepository.updateTransaction(tr);
    }

    // Delete a transaction
    public int deleteUser(long idTransaction) {
        return transactionRepository.deleteUser(idTransaction);
    }
}
