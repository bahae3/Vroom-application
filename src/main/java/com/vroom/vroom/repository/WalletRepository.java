package com.vroom.vroom.repository;

import com.vroom.vroom.model.User;
import com.vroom.vroom.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Wallet> walletRowMapper = (rs, rowNum) -> new Wallet(
            rs.getLong("idWallet"),
            rs.getLong("idUser"),
            rs.getDouble("solde")
    );

    // List all wallets in db (for admin)
    public List<Wallet> getAllWallets() {
        String sql = "SELECT * FROM wallet";
        return jdbcTemplate.query(sql, walletRowMapper);
    }

    // Create a new wallet
    // ps: it should be created automatically after signup
    // solde will be 0.00MAD automatically
    public int createWallet(long idUser) {
        String sql = "INSERT INTO wallet (idUser) VALUES (?)";
        return jdbcTemplate.update(sql, idUser);
    }

    // Update wallet data
    // This can be used to update balance (Solde) of a wallet after a transaction
    public int updateWallet(Wallet wallet) {
        String sql = "UPDATE wallet set solde=? WHERE idWallet = ? ";
        return jdbcTemplate.update(
                sql,
                wallet.getSolde(),
                wallet.getIdWallet()
        );
    }

    // Delete a wallet, maybe by admin and when a user account is suspended
    public int deleteWallet(long idWallet) {
        String sql = "DELETE FROM wallet WHERE idWallet = ?";
        return jdbcTemplate.update(sql, idWallet);
    }

    // Find wallet by user (used in update account)
    public Wallet findWalletByUser(long idUser){
        String sql = "SELECT * FROM wallet WHERE idUser = ?";
        try {
            return jdbcTemplate.queryForObject(sql, walletRowMapper, idUser);
        } catch (EmptyResultDataAccessException ex) {
            // No wallet found with the given id
            return null;
        }
    }

}
