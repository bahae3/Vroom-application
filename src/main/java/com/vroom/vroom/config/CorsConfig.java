package com.vroom.vroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration CORS pour gérer les règles de partage de ressources entre origines.
 *
 * Cette classe configure les paramètres CORS (Cross-Origin Resource Sharing) pour permettre à l'application
 * d'accepter des requêtes HTTP provenant de différents domaines. Elle permet de contrôler les origines et
 * les méthodes HTTP autorisées pour les appels entre différents domaines.
 */
@Configuration
public class CorsConfig {

    /**
     * Crée un bean WebMvcConfigurer pour personnaliser la configuration CORS.
     *
     * @return Un WebMvcConfigurer configuré avec les règles CORS définies.
     *
     * Cette méthode configure les règles CORS en ajoutant des mappages pour autoriser les requêtes provenant
     * de toutes les origines avec certains types de méthodes HTTP.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Ajoute les configurations CORS pour l'application.
             *
             * @param registry L'objet CorsRegistry utilisé pour enregistrer les règles CORS.
             *
             * Cette méthode configure les mappages CORS, autorisant l'accès aux ressources de l'application
             * à partir de toutes les origines (`allowedOrigins("*")`) et en définissant les méthodes HTTP
             * autorisées pour ces requêtes.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Applique la configuration CORS à toutes les routes de l'application
                        .allowedOrigins("*")  // Autorise toutes les origines (par exemple, tous les domaines)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");  // Autorise ces méthodes HTTP
            }
        };
    }
}
