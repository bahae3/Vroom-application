package com.vroom.vroom.repository;

import com.vroom.vroom.model.Trajets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public class TrajetsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public TrajetsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    private final RowMapper<Trajets> trajetsRowMapper = (rs, rowNum) -> {
        Trajets trajet = new Trajets();
        trajet.setIdTrajet(rs.getInt("idTrajet"));
        trajet.setIdConducteur(rs.getInt("idConducteur"));
        trajet.setPointDepart(rs.getString("pointDepart"));
        trajet.setPointArrivee(rs.getString("pointArrivee"));
        trajet.setHeureDepart(rs.getTimestamp("heureDepart").toLocalDateTime());
        trajet.setPlacesDisponibles(rs.getInt("placesDisponibles"));
        trajet.setPrix(rs.getFloat("prix"));
        trajet.setEtat(rs.getString("etat"));
        return trajet;
    };




    public List<Trajets> getAllTrajets() {
        String sql = "SELECT * FROM trajet";
        return jdbcTemplate.query(sql, trajetsRowMapper);

    }


    // Add Trajet par un conducteur seulement
    public int AddTrajet(Trajets trajet) {
        String sql = "INSERT  INTO trajet (idConducteur, pointDepart, pointArrivee, heureDepart, placesDisponibles, prix, etat)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                trajet.getIdConducteur(),
                trajet.getPointDepart(),
                trajet.getPointArrivee(),
                Timestamp.valueOf(trajet.getHeureDepart()), // conversion LocalDateTime → Timestamp
                trajet.getPlacesDisponibles(),
                trajet.getPrix(),
                trajet.getEtat()
        );

    }

    // modifier un trajet par un conducteur
    public int UpdateTrajet(Trajets trajet) {
        String sql = "UPDATE trajet SET pointDepart=?, pointArrivee=?, heureDepart=?, placesDisponibles=?, prix=?, etat=? WHERE idTrajet=? ";
        return jdbcTemplate.update(
                sql,
                trajet.getPointDepart(),
                trajet.getPointArrivee(),
                trajet.getHeureDepart(),
                trajet.getPlacesDisponibles(),
                trajet.getPrix(),
                trajet.getEtat(),
                trajet.getIdTrajet()
        );
    }


    // delete trajet par un conducteur
    public int DeleteTrajet(int idTrajet, long idConducteur) {
        String sql = "DELETE FROM trajet WHERE idTrajet=? AND idConducteur=?";
        return jdbcTemplate.update(sql, idTrajet, idConducteur);


    }

    public Trajets getTrajetById(int idTrajet) {
        String sql = "SELECT * FROM trajet WHERE idTrajet = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{ idTrajet },
                trajetsRowMapper
        );
    }


}
