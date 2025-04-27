package com.vroom.vroom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service de géolocalisation pour interagir avec une API de routage externe (OSRM).
 * Ce service permet de récupérer des itinéraires routiers entre deux points géographiques.
 */
@Service
public class GeolocationService {

    // Client Web réactif utilisé pour envoyer des requêtes HTTP.
    private final WebClient webClient;

    /**
     * Constructeur du service GeolocationService.
     *
     * @param webClient le WebClient injecté pour effectuer les appels externes
     */
    public GeolocationService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Récupère un itinéraire entre deux positions géographiques en appelant l'API OSRM.
     *
     * @param startLat latitude du point de départ
     * @param startLng longitude du point de départ
     * @param endLat latitude du point d'arrivée
     * @param endLng longitude du point d'arrivée
     * @return un objet Mono contenant la réponse brute (format JSON) sous forme de chaîne de caractères
     */
    public Mono<String> getRoute(double startLat, double startLng,
                                 double endLat, double endLng) {
        // Construction du chemin URL pour l'API OSRM avec les coordonnées fournies
        String path = String.format(
                "/route/v1/driving/%.6f,%.6f;%.6f,%.6f?overview=full&geometries=geojson",
                startLng, startLat, endLng, endLat
        );

        // Envoi de la requête GET vers l'API OSRM et récupération de la réponse sous forme de Mono<String>
        return webClient
                .get() // Type de requête : GET
                .uri("http://router.project-osrm.org" + path) // URL complète vers le service OSRM
                .retrieve() // Déclenche la requête et prépare à extraire la réponse
                .bodyToMono(String.class); // Conversion de la réponse JSON en Mono de String
    }
}
