package com.vroom.vroom.controller;

import com.vroom.vroom.model.Trajets;
import com.vroom.vroom.model.User;
import com.vroom.vroom.service.TrajetsService;
import com.vroom.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/trajet")
public class TrajetsController {

    @Autowired
    private TrajetsService trajetsService;
    @Autowired
    private UserService userService;


    // partie controller pour ajouter trajet par conducteur seulement
    @PostMapping("/addTrajet")
    public String addTrajet( @RequestBody Trajets trajets) {
       User user = userService.findUserById(trajets.getIdConducteur());

       if(user == null) {
           return "conducteur non trouvé";
       }
       if (!"conducteur".equalsIgnoreCase(user.getRoleUser().trim())){
           return "unauthorized to acces conducteur";
       }
       boolean result = trajetsService.AddTrajet(trajets);
       return result ? "Trajet ajouté avec succès" : "Échec lors de l'ajout du trajet";
    }

    // modfier le trajet juste du cote conducteur
    @PutMapping("/updateTrajet")
    public String updateTrajet(@RequestBody Trajets trajets) {
        User user = userService.findUserById((long) trajets.getIdConducteur());
        if(user == null) {
            return "conducteur non trouvé ";
        }
        if (!"conducteur".equalsIgnoreCase(user.getRoleUser().trim().toLowerCase())){
            return "Unauthorized: seul un conducteur peut modifier un trajet.";
        }
        boolean result = trajetsService.UpdateTrajet(trajets);
        return result ? "trajet modifié avec succes" : "echec lors de la modification du trajet";
    }


    //supprimer un trajet du cote conducteur
    @DeleteMapping("/deleteTrajet")
    public String deleteTrajet(@RequestBody Trajets trajets) {
        User user = userService.findUserById((long) trajets.getIdConducteur());
        if(user == null) {
            return "conducteur non trouvé";
        }
        if (!"conducteur".equalsIgnoreCase(user.getRoleUser().trim().toLowerCase())){
            return "unauthorized to acces conducteur";
        }
        boolean result =trajetsService.DeleteTrajet(trajets.getIdTrajet(),trajets.getIdConducteur());
        return result ? "le trajet est supprimé par succes" : "echec lors de la suppression du trajet";

    }

    // get All trajet controller
    @GetMapping("/getAllTrajet")
    public List<Trajets> getAllTrajets() {
        return trajetsService.getAllTrajets();
    }

    @GetMapping("/getById/{idTrajet}")
    public ResponseEntity<?> getTrajetById(@PathVariable long idTrajet) {
        Trajets t = trajetsService.getTrajetById((int) idTrajet);
        if (t == null) {
            return ResponseEntity.status(404).body("Trajet introuvable");
        }
        return ResponseEntity.ok(t);
    }


}
