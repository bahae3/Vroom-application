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
@RequestMapping("/api/passager")
@CrossOrigin(origins = "http://localhost:5173")
public class PassagerController {

    @Autowired private UserService userService;
    @Autowired private TrajetsService trajetsService;


    // Authentification
    private boolean isPassager(Authentication authentication) {
        User currentUser = userService.findUserByEmail(authentication.getName());
        return currentUser != null && "passager".equals(currentUser.getRoleUser());
    }

    //voir les trajets disponibles
    @GetMapping("/trajets")
    public ResponseEntity<?> getAllTrajet(Authentication authentication){
        if (!isPassager(authentication) ) { return ResponseEntity.status(403).body("Acces denied"); }
        User passager= userService.findUserByEmail(authentication.getName());
        List<Trajets> trajets = trajetsService.getAllTrajets();
        if (trajets.isEmpty()) {
            return ResponseEntity.status(404).body("Pas encore de trajets");
        }
        else {
            return ResponseEntity.ok(trajets);
        }
    }

    // voir son profil
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication){
        if (!isPassager(authentication)) { return ResponseEntity.status(403).body("Acces denied"); }
        User passager= userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(passager);
    }

    //Confirmation de reservation du trajet
    @PostMapping("/reserver")
    public ResponseEntity<?> reserveTrajet(@RequestParam int trajetId, Authentication authentication) {
        if( !isPassager(authentication) ) { return ResponseEntity.status(403).body("Acces denied"); }
        User passager= userService.findUserByEmail(authentication.getName());
        boolean succes = trajetsService.reserverTrajet(trajetId, passager.getIdUser());
        return succes ? ResponseEntity.ok("le trajet est confirmé") : ResponseEntity.status(400).body("erreur lors de la reservation");
    }




}
