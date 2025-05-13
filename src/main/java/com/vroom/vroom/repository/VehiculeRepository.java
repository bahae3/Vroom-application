package com.vroom.vroom.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.vroom.vroom.model.Vehicule;

import java.util.List;

@Repository
public class VehiculeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //Ajouter une vehicule
    public int addVehicule(Vehicule vehicule) {
        String sql="Insert into vehicule (idConducteur, marque, typeVehicule, matricule, couleur) values (?,?,?,?,?)";
        return jdbcTemplate.update(
                sql,
                vehicule.getIdConducteur(),
                vehicule.getMarque(),
                vehicule.getTypeVehicule(),
                vehicule.getMatricule(),
                vehicule.getCouleur()
        );
    }
    //Modifier une vehicule
    public int updateVehicule(Vehicule vehicule) {
        String sql="Update vehicule set idConducteur=?, marque=?, typeVehicule=?, matricule=?, couleur=? where idVehicule=?";
        return jdbcTemplate.update(
                sql,
                vehicule.getIdConducteur(),
                vehicule.getMarque(),
                vehicule.getTypeVehicule(),
                vehicule.getMatricule(),
                vehicule.getCouleur(),
                vehicule.getIdVehicule()
        );

    }

    //Supprimer une vehicule
    public int deleteVehicule(int idVehicule) {
        String sql="Delete from vehicule where idVehicule=?";
        return jdbcTemplate.update(sql, idVehicule);
    }

    //voir vehicule par son id du conducteur
    public List<Vehicule> getVehiculesByConducteur(Long idConducteur) {
        String sql="Select * from vehicule where idConducteur=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Vehicule.class), idConducteur);
    }




}
