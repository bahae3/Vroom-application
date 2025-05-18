package com.vroom.vroom.service;

import com.vroom.vroom.model.Trajets;
import com.vroom.vroom.repository.TrajetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

@Service
public class TrajetsService {

    @Autowired
    private TrajetsRepository trajetsRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplate jdbc;

    // get All trajet service
    public List<Trajets> getAllTrajets() {
        return trajetsRepository.getAllTrajets();
    }


    // Add trajet par un conducteur service
    public  boolean AddTrajet(Trajets trajets) {
        return trajetsRepository.AddTrajet(trajets) >0;

    }

    // modifier le trajet par un conducteur service
    public boolean UpdateTrajet(Trajets trajets) {
        return trajetsRepository.UpdateTrajet(trajets) >0;
    }

    //delete trajet service
    public boolean DeleteTrajet(int idTrajet, int idConducteur) {
        return  trajetsRepository.DeleteTrajet(idTrajet , idConducteur) >0;
    }

    // implementation pour la reservation d'un trajet par un conducteur
    public boolean reserverTrajet(int idUser, int idTrajet) {
        // 1. Check if trajet exists and if places are available
        String checkSql = "SELECT placesDisponibles FROM trajet WHERE idTrajet = ?";
        Integer places = jdbcTemplate.queryForObject(checkSql, Integer.class, idTrajet);

        if (places == null || places <= 0) {
            return false;
        }

        // 2. Insert into historiqueTrajet (booking)
        String insertSql = "INSERT INTO historiqueTrajet (idUser, idTrajet) VALUES (?, ?)";
        int insertResult = jdbcTemplate.update(insertSql, idUser, idTrajet);

        if (insertResult <= 0) return false;

        // 3. Decrease available seats
        String updateSql = "UPDATE trajet SET placesDisponibles = placesDisponibles - 1 WHERE idTrajet = ?";
        int updateResult = jdbcTemplate.update(updateSql, idTrajet);

        return updateResult > 0;
    }

    public boolean deleteTrajetAdmin(int trajetId) {
        String sql = "DELETE FROM trajet WHERE idTrajet = ?";
        int rows = jdbc.update(sql, trajetId);
        return rows > 0;
    }



}
