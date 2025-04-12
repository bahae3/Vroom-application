package com.vroom.vroom.repository;

import com.vroom.vroom.model.HistoriqueTrajet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoriqueTrajetRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<HistoriqueTrajet> historiqueTrajetRowMapper = (rs, rowNum) -> new HistoriqueTrajet(
            rs.getLong("idHistorique"),
            rs.getLong("idUser"),
            rs.getLong("idTrajet")
    );

    // List all historique in db by user id
    public List<HistoriqueTrajet> getAllHistorique(long idUser) {
        String sql = "SELECT * FROM historiquetrajet WHERE idUser=?";
        return jdbcTemplate.query(sql, historiqueTrajetRowMapper, idUser);
    }

    // Create a new historique de trajet
    public int createHistoriqueTrajet(HistoriqueTrajet ht) {
        String sql = "INSERT INTO historiquetrajet (idUser, idTrajet) VALUES (?, ?)";
        return jdbcTemplate.update(sql, ht.getIdUser(), ht.getIdTrajet());
    }

    // Delete a historique
    public int deleteHistorique(long idHistorique) {
        String sql = "DELETE FROM historiquetrajet WHERE idHistorique = ?";
        return jdbcTemplate.update(sql, idHistorique);
    }
}
