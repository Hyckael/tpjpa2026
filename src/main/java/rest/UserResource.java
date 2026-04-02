package rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import dao.UserDao;
import entity.User;

import java.util.List;

@Path("user")
@Produces({"application/json", "application/xml"})
@Tag(name = "Utilisateur", description = "Gestion des utilisateurs(Clients, organisateurs, admins)")
public class UserResource {

    private final UserDao userDao = new UserDao();

    @GET
    @Path("/{userId}")
    @Operation(summary = "Récupérer un utilisateur par son Id")
    @ApiResponse(responseCode = "200", description = "Utilisateur trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public User getUserByID(@PathParam("userId") Long userId) {
        User usr = userDao.findOne(userId);
        if (usr == null) {
            throw new WebApplicationException("Aucun utilisateur trouvé", Response.Status.NOT_FOUND);
        }
        return usr;
    }

    @GET
    @Path("/")
    @Operation(summary = "Récupérer tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Utilisateurs trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public List<User> getAllUser() {
        List<User> users = userDao.findAll();

        if (users == null) {
            throw new WebApplicationException("Aucun utilisateur trouvé", Response.Status.NOT_FOUND);
        }

        return users;
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    @Operation(summary = "Création d'un utilisateur")
    @ApiResponse(responseCode = "200", description = "utilisateur créé")
    @ApiResponse(responseCode = "404", description = "echec")
    public Response addUser (User user) {
            try {
                userDao.save(user);
                return Response.ok().entity("SUCCESS").build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
    }

    @DELETE
    @Path("/{userId}")
    @Operation(summary = "Suppression d'un utilisateur")
    @ApiResponse(responseCode = "200", description = "Utilisateur trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public Response deleteUser(@PathParam("userId") Long userId) {
        try {
            User user = userDao.findOne(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Utilisateur non trouvé")
                        .build();
            }
            userDao.delete(user);
            return Response.ok()
                    .entity("utilisateur supprimé")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
