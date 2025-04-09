package com.vroom.vroom.service;

import com.vroom.vroom.model.Trajets;
import com.vroom.vroom.repository.TrajetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrajetsService {

    @Autowired
    private TrajetsRepository trajetsRepository;

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






}
