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

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public int createTransaction(Transaction tr) {
        return transactionRepository.createTransaction(tr);
    }

    public int updateTransaction(Transaction tr) {
        return transactionRepository.updateTransaction(tr);
    }

    // Renamed from deleteUser to deleteTransaction
    public int deleteTransaction(long idTransaction) {
        return transactionRepository.deleteTransaction(idTransaction);
    }
}
