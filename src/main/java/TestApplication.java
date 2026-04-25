import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import rest.ArtistResource;
import rest.CorsFilter;
import rest.EventResource;
import rest.UserResource;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "API Gestion d'événements",
                version = "1.0.0",
                description = "API REST pour la gestion des événements"
        )
)
public class TestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazzes = new HashSet<>();
        clazzes.add(OpenApiResource.class);
        clazzes.add(UserResource.class);
        clazzes.add(EventResource.class);
        clazzes.add(ArtistResource.class);
        clazzes.add(SwaggerResource.class);
        clazzes.add(CorsFilter.class);
        return clazzes;
    }
}