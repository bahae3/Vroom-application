package com.vroom.vroom.repository;

import com.vroom.vroom.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> new Transaction(
            rs.getLong("idTransaction"),
            rs.getLong("idWallet"),
            rs.getString("typeTransaction"),
            rs.getDouble("montant"),
            rs.getDate("dateTransaction")
    );

    // List all transactions in db
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, transactionRowMapper);
    }

    // Create a new transaction
    // used after a tranasction is made: payment - 3amarti lwallet (deposit) ...
    public int createTransaction(Transaction tr) {
        String sql = "INSERT INTO transactions (idWallet, typeTransaction, montant) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, tr.getIdWallet(), tr.getTypeTransaction(), tr.getMontant());
    }

    // Update a transaction
    public int updateTransaction(Transaction tr) {
        String sql = "UPDATE transactions set typeTransaction=?, montant=? WHERE idTransaction = ? ";
        return jdbcTemplate.update(
                sql,
                tr.getTypeTransaction(),
                tr.getMontant()
        );
    }

    // Delete a transaction
    // ps: will not often be used
    public int deleteUser(long idTransaction) {
        String sql = "DELETE FROM transactions WHERE idTransaction = ?";
        return jdbcTemplate.update(sql, idTransaction);
    }
}
