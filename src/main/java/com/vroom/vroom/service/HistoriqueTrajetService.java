package com.vroom.vroom.service;

import com.vroom.vroom.model.HistoriqueTrajet;
import com.vroom.vroom.repository.HistoriqueTrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueTrajetService {
    @Autowired
    private HistoriqueTrajetRepository historiqueTrajetRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // List all historique in db by user id
    public List<HistoriqueTrajet> getAllHistorique(long idUser) {
        return historiqueTrajetRepository.getAllHistorique(idUser);
    }

    // Create a new historique de trajet
    public int createHistoriqueTrajet(HistoriqueTrajet ht) {
        return historiqueTrajetRepository.createHistoriqueTrajet(ht);
    }

    // Delete a historique
    public int deleteHistorique(long idHistorique) {
        return historiqueTrajetRepository.deleteHistorique(idHistorique);
    }
    public boolean existsByUserAndTrajet(long idUser, long idTrajet) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM historiquetrajet WHERE idUser = ? AND idTrajet = ?",
                Integer.class,
                idUser, idTrajet
        );
        return count != null && count > 0;
    }
}
