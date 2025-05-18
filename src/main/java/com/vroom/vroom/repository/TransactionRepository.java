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

    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, transactionRowMapper);
    }

    public int createTransaction(Transaction tr) {
        String sql = "INSERT INTO transactions (idWallet, typeTransaction, montant) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, tr.getIdWallet(), tr.getTypeTransaction(), tr.getMontant());
    }

    public int updateTransaction(Transaction tr) {
        String sql = "UPDATE transactions SET typeTransaction=?, montant=? WHERE idTransaction=?";
        return jdbcTemplate.update(
                sql,
                tr.getTypeTransaction(),
                tr.getMontant(),
                tr.getIdTransaction()
        );
    }

    // Renamed from deleteUser to deleteTransaction
    public int deleteTransaction(long idTransaction) {
        String sql = "DELETE FROM transactions WHERE idTransaction = ?";
        return jdbcTemplate.update(sql, idTransaction);
    }
}