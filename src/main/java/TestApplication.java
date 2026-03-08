import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import rest.UserResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class TestApplication extends Application {


    @Override
    public Set<Class<?>> getClasses() {

        final Set<Class<?>> clazzes = new HashSet<Class<?>>();

        clazzes.add(OpenApiResource.class);
        clazzes.add(UserResource.class);
//        clazzes.add(AcceptHeaderOpenApiResource.class);


        return clazzes;
    }

}