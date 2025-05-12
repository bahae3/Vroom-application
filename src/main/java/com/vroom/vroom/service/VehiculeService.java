package com.vroom.vroom.service;


import com.vroom.vroom.model.Vehicule;
import com.vroom.vroom.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //service pour voir vehicule par conducteur
    public List<Vehicule> getVehiculesByConducteur(int idConducteur) {
        return vehiculeRepository.getVehiculesByConducteur(idConducteur);
    }

    //Service pour ajouter une vehicule
    public boolean addVehicule(Vehicule vehicule) {
        return vehiculeRepository.addVehicule(vehicule)>0;
    }

    //Modifier une vehicule
    public boolean updateVehicule(Vehicule vehicule) {
        return vehiculeRepository.updateVehicule(vehicule)>0;
    }

    //Supprimer une vehicule
    public boolean deleteVehicule(int idVehicule) {
        return vehiculeRepository.deleteVehicule(idVehicule)>0;
    }


}
