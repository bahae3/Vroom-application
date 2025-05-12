package com.vroom.vroom.controller;


import com.vroom.vroom.model.User;
import com.vroom.vroom.model.Vehicule;
import com.vroom.vroom.repository.VehiculeRepository;
import com.vroom.vroom.service.UserService;
import com.vroom.vroom.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private boolean isConducteur(Authentication authentication) {
        User currentUser = userService.findUserByEmail(authentication.getName());
        return currentUser != null && "conducteur".equals(currentUser.getRoleUser());

    }

    //  Ajouter un véhicule
    @PostMapping
    public ResponseEntity<?> addVehicule(@RequestBody Vehicule vehicule, Authentication authentication) {
        if (!isConducteur(authentication)) return ResponseEntity.status(403).body("Accès refusé");

        User conducteur = userService.findUserByEmail(authentication.getName());
        vehicule.setIdConducteur(conducteur.getIdUser());
        boolean success = vehiculeService.addVehicule(vehicule);

        return success ? ResponseEntity.ok("Véhicule ajouté avec succès")
                : ResponseEntity.status(500).body("Erreur lors de l'ajout du véhicule");
    }

    //  Modifier un véhicule
    @PutMapping
    public ResponseEntity<?> updateVehicule(@RequestBody Vehicule vehicule, Authentication auth) {
        if (!isConducteur(auth)) return ResponseEntity.status(403).body("Accès refusé");

        User conducteur = userService.findUserByEmail(auth.getName());
        vehicule.setIdConducteur(conducteur.getIdUser());
        boolean success = vehiculeService.updateVehicule(vehicule);

        return success ? ResponseEntity.ok("Véhicule modifié avec succès")
                : ResponseEntity.status(500).body("Erreur lors de la modification");
    }

    //  Supprimer un véhicule
    @DeleteMapping("/{idVehicule}")
    public ResponseEntity<?> deleteVehicule(@PathVariable int idVehicule, Authentication auth) {
        if (!isConducteur(auth)) return ResponseEntity.status(403).body("Accès refusé");

        boolean success = vehiculeService.deleteVehicule(idVehicule);
        return success ? ResponseEntity.ok("Véhicule supprimé avec succès")
                : ResponseEntity.status(500).body("Erreur lors de la suppression");
    }

    //  Voir les véhicules du conducteur connecté
    @GetMapping
    public ResponseEntity<?> getVehiculesConducteur(Authentication auth) {
        if (!isConducteur(auth)) return ResponseEntity.status(403).body("Accès refusé");

        User conducteur = userService.findUserByEmail(auth.getName());
        List<Vehicule> vehicules = vehiculeService.getVehiculesByConducteur(conducteur.getIdUser());

        if (vehicules.isEmpty()) return ResponseEntity.status(404).body("Aucun véhicule trouvé");
        return ResponseEntity.ok(vehicules);
    }

}
