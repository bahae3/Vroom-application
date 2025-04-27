package com.vroom.vroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration WebClient pour effectuer des appels HTTP réactifs.
 *
 * Cette classe configure un bean `WebClient`, qui permet d'effectuer des requêtes HTTP de manière réactive
 * dans l'application Spring, en utilisant une approche non bloquante.
 */
@Configuration
public class WebClientConfig {

    /**
     * Crée un bean WebClient configuré à l'aide d'un constructeur.
     *
     * @param builder Le constructeur `WebClient.Builder` qui permet de personnaliser l'instance de WebClient.
     * @return Une instance de WebClient prête à être utilisée pour effectuer des requêtes HTTP.
     *
     * Ce bean peut être injecté dans d'autres parties de l'application pour effectuer des appels HTTP
     * réactifs vers des API externes ou internes.
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        // Utilise le builder pour créer une instance de WebClient
        return builder.build();
    }
}
