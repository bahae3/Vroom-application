package com.vroom.vroom.controller;
import com.vroom.vroom.model.User;
import com.vroom.vroom.model.Trajets;
import com.vroom.vroom.service.TrajetsService;
import com.vroom.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Conducteur")
@CrossOrigin(origins = "http://localhost:5173")
public class ConducteurController {

    @Autowired private UserService userService;
    @Autowired private TrajetsService trajetsService;

    private boolean isConducteur(Authentication authentication) {
        User currentUser = userService.findUserByEmail(authentication.getName());
        return currentUser != null && "conducteur".equals(currentUser.getRoleUser());
    }

    //ajouter un trajet
    @PostMapping("/trajet")
    public ResponseEntity<?> addTrajet(@RequestBody Trajets trajet, Authentication authentication) {
        if (!isConducteur(authentication)) { return ResponseEntity.status(403).body("Acces denied"); }

        User conducteur = userService.findUserByEmail(authentication.getName());
        trajet.setIdConducteur(conducteur.getIdUser());
        boolean add = trajetsService.AddTrajet(trajet);
        return add ? ResponseEntity.ok().body("Trajet added successfully") : ResponseEntity.status(500).body("erreur lors de l'ajout du trajet");
    }

    //modifier un trajet
    @PostMapping("/trajet/update")
    public ResponseEntity<?> updateTrajet(@RequestBody Trajets trajet, Authentication authentication) {
        if (!isConducteur(authentication)) { return ResponseEntity.status(403).body("Acces denied"); }
        User conducteur = userService.findUserByEmail(authentication.getName());
        if (trajet.getIdConducteur() != conducteur.getIdUser()) { return ResponseEntity.status(403).body("Vous pouvez pas modifier le trajet"); }

        boolean update = trajetsService.UpdateTrajet(trajet);
        return update ? ResponseEntity.ok("trajet modifié") : ResponseEntity.status(500).body("erreur lors de la modification");
    }

    //Supprimer un trajet
    @DeleteMapping("/trajet/{id}")
    public ResponseEntity<?> deleteTrajet(@PathVariable int id, Authentication authentication) {
        if (!isConducteur(authentication)) { return ResponseEntity.status(403).body("Acces denied"); }
        User conducteur = userService.findUserByEmail(authentication.getName());

        boolean delete =trajetsService.DeleteTrajet(id ,conducteur.getIdUser());
        return delete ? ResponseEntity.ok("Suppression avec succes") : ResponseEntity.status(500).body("erreur lors de la suppression");
    }

    // lister les trajet par conducteur
    @GetMapping("/mesTrajets")
    public ResponseEntity<?> getAllTrajets(Authentication authentication) {
        if (!isConducteur(authentication)){ return ResponseEntity.status(403).body("Acces denied"); }
        User conducteur = userService.findUserByEmail(authentication.getName());
       List<Trajets> trajet=trajetsService.getAllTrajets();
       if(trajet.isEmpty()){
           return ResponseEntity.status(404).body("la liste des trajets est vide");
       }
       else {
           return ResponseEntity.ok(trajet);
       }
    }

    //voir le profile
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (!isConducteur(authentication)) { return ResponseEntity.status(403).body("Acces denied"); }
        User conducteur = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(conducteur);
    }



}
