package rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
@OpenAPIDefinition(
        info = @Info(
                title = "API Gestion d'événements",
                version = "1.0.0",
                description = "API REST pour la gestion des événements"
        )
)
public class ApplicationConfig {
}
