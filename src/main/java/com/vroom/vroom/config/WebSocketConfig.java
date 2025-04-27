package com.vroom.vroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration WebSocket pour gérer la communication en temps réel via STOMP et SockJS.
 *
 * Cette classe configure le courtier de messages (broker) pour le traitement des messages WebSocket
 * et définit les points de terminaison STOMP pour établir les connexions entre le client et le serveur.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure le broker de messages.
     *
     * @param config L'objet MessageBrokerRegistry utilisé pour configurer les brokers.
     *
     * Cette méthode configure un broker simple qui supporte les destinations préfixées par `/topic`
     * et définit un préfixe d'application `/app` pour l'envoi de messages destinés à des méthodes spécifiques sur le serveur.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Active un broker simple pour les messages avec le préfixe "/topic"
        config.enableSimpleBroker("/topic");

        // Définit le préfixe "/app" pour les destinations d'application, utilisées pour les méthodes de traitement des messages
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Enregistre les points de terminaison STOMP pour établir les connexions WebSocket.
     *
     * @param registry L'objet StompEndpointRegistry utilisé pour enregistrer les points de terminaison.
     *
     * Cette méthode permet de définir un point de terminaison pour la connexion WebSocket.
     * Elle permet également de configurer les options CORS et d'ajouter le support de SockJS pour une meilleure compatibilité.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Enregistre le point de terminaison "/ws-geolocation" pour établir la connexion STOMP
        registry
                .addEndpoint("/ws-geolocation")  // Point de terminaison STOMP
                .setAllowedOriginPatterns("*")    // Autorise toutes les origines (CORS)
                .withSockJS();                   // Active SockJS pour les connexions WebSocket
    }
}
