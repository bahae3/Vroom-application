package com.vroom.vroom.service;

import com.vroom.vroom.model.Wallet;
import com.vroom.vroom.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    // List all wallets in db (for admin)
    public List<Wallet> getAllWallets() {
        return walletRepository.getAllWallets();
    }


    // Create a new wallet
    public int createWallet(long idUser) {
        return walletRepository.createWallet(idUser);
    }


    // Update wallet data
    public int updateWallet(Wallet wallet) {
        return walletRepository.updateWallet(wallet);
    }

    // Delete a wallet
    public int deleteWallet(long idWallet) {
        return walletRepository.deleteWallet(idWallet);
    }

    // Find wallet by user
    public Wallet findWalletByUser(long idUser){
        return walletRepository.findWalletByUser(idUser);
    }
}
