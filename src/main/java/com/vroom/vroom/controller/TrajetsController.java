package com.vroom.vroom.controller;

import com.vroom.vroom.model.Trajets;
import com.vroom.vroom.model.User;
import com.vroom.vroom.service.GeolocationService;
import com.vroom.vroom.service.TrajetsService;
import com.vroom.vroom.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.vroom.vroom.service.*;

import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/trajet")
public class TrajetsController {

    private final TrajetsService trajetsService;
    private final UserService userService;
    private final GeolocationService geolocationService;
    private final LocationBroadcastService broadcaster;

    /**
     * Constructeur du contrôleur TrajetsController.
     *
     * @param trajetsService Service pour gérer les trajets.
     * @param userService Service pour gérer les utilisateurs.
     * @param geolocationService Service pour récupérer les informations géographiques (itinéraire, temps de trajet).
     * @param broadcaster Service pour diffuser la position des utilisateurs.
     */
    public TrajetsController(TrajetsService trajetsService,
                             UserService userService,
                             GeolocationService geolocationService,
                             LocationBroadcastService broadcaster) {
        this.trajetsService = trajetsService;
        this.userService = userService;
        this.geolocationService = geolocationService;
        this.broadcaster = broadcaster;
    }

    /**
     * Récupère l'itinéraire et le temps de trajet entre deux points géographiques.
     *
     * @param startLat Latitude du point de départ.
     * @param startLng Longitude du point de départ.
     * @param endLat Latitude du point d'arrivée.
     * @param endLng Longitude du point d'arrivée.
     * @return Mono<String> Un objet contenant l'itinéraire et le temps estimé de trajet.
     */
    @GetMapping("/route")
    public Mono<String> route(@RequestParam double startLat,
                              @RequestParam double startLng,
                              @RequestParam double endLat,
                              @RequestParam double endLng) {
        // Appel du service GeolocationService pour récupérer l'itinéraire
        return geolocationService.getRoute(startLat, startLng, endLat, endLng);
    }

    /**
     * Diffuse la position géographique d'un utilisateur en temps réel.
     *
     * @param payload Objet contenant les informations à diffuser (userId, lat, lng).
     */
    @PostMapping("/broadcast")
    public void broadcastLocation(@RequestBody Map<String, Object> payload) {
        // Extraction des informations depuis le corps de la requête
        Long userId = Long.valueOf(payload.get("userId").toString());
        double lat  = Double.parseDouble(payload.get("lat").toString());
        double lng  = Double.parseDouble(payload.get("lng").toString());

        // Diffusion de la position via le service LocationBroadcastService
        broadcaster.broadcastPosition(userId, lat, lng);
    }


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


}
