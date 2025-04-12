package com.vroom.vroom.controller;

import com.vroom.vroom.model.HistoriqueTrajet;
import com.vroom.vroom.service.HistoriqueTrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueTrajetController {

    @Autowired
    private HistoriqueTrajetService historiqueTrajetService;

    // Get all historiques for a specific user
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<HistoriqueTrajet>> getAllHistorique(@PathVariable long idUser) {
        List<HistoriqueTrajet> historiques = historiqueTrajetService.getAllHistorique(idUser);
        return ResponseEntity.ok(historiques);
    }

    // Create a new historique
    @PostMapping
    public ResponseEntity<String> createHistorique(@RequestParam("idUser") long idUser,
                                                   @RequestParam("idTrajet") long idTrajet) {
        HistoriqueTrajet historique = new HistoriqueTrajet(0, idUser, idTrajet);
        int result = historiqueTrajetService.createHistoriqueTrajet(historique);
        if (result > 0) {
            return ResponseEntity.ok("Historique created successfully.");
        } else {
            return ResponseEntity.status(500).body("Error creating historique.");
        }
    }

    // Delete an historique by its ID
    @DeleteMapping("/{idHistorique}")
    public ResponseEntity<String> deleteHistorique(@PathVariable long idHistorique) {
        int result = historiqueTrajetService.deleteHistorique(idHistorique);
        if (result > 0) {
            return ResponseEntity.ok("Historique deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Historique not found.");
        }
    }
}
