package com.vroom.vroom.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

/**
 * Service pour diffuser en temps réel la position d'un utilisateur
 * via WebSocket (topic "/topic/locations").
 */
@Service
public class LocationBroadcastService {

    // Template pour envoyer des messages via WebSocket
    private final SimpMessagingTemplate template;

    /**
     * Constructeur pour injecter le SimpMessagingTemplate utilisé pour l'envoi de messages.
     *
     * @param template SimpMessagingTemplate injecté par Spring
     */
    public LocationBroadcastService(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Diffuse la position d'un utilisateur à tous les clients connectés au topic "/topic/locations".
     *
     * @param userId l'identifiant de l'utilisateur
     * @param lat latitude actuelle de l'utilisateur
     * @param lng longitude actuelle de l'utilisateur
     */
    public void broadcastPosition(Long userId, double lat, double lng) {
        // Création d'un message contenant l'ID utilisateur, les coordonnées et l'horodatage actuel
        Map<String, Object> msg = Map.of(
                "userId", userId,
                "lat", lat,
                "lng", lng,
                "timestamp", Instant.now().toString() // Format ISO 8601 du temps actuel
        );

        // Envoi du message sur le canal "/topic/locations" pour être reçu par tous les abonnés
        template.convertAndSend("/topic/locations", msg);
    }
}
