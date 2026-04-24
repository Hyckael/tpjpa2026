package rest;

import dto.LoginDto;
import dto.LoginResponseDto;
import dto.UserDto;
import entity.Admin;
import entity.Client;
import entity.Organizer;
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

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Operation(summary = "Connexion d'un utilisateur")
    public Response login(LoginDto dto) {
        try {
            User user = userDao.findByEmail(dto.getEmail());

            if (user == null || !user.getPassword().equals(dto.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Email ou mot de passe incorrect")
                        .build();
            }

            // Détermine le rôle selon le type réel de l'objet
            String role;
            if (user instanceof Organizer) role = "organizer";
            else if (user instanceof Admin) role = "admin";
            else role = "user";

            // Retourne les infos utiles au front
            LoginResponseDto response = new LoginResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    role
            );

            return Response.ok(response).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
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
    public Response addUser (UserDto dto) {
        try {
            User user;
            switch (dto.getRole()) {
                case "organizer":
                    Organizer org = new Organizer();
                    org.setCompanyName(dto.getCompanyName() != null ? dto.getCompanyName() : "");
                    user = org;
                    break;
                case "user":
                default:
                    Client client = new Client();
                    client.setCity(dto.getCity() != null ? dto.getCity() : "");
                    user = client;
                    break;
            }
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword()); // à hasher en prod !
            user.setAddress(dto.getAddress() != null ? dto.getAddress() : "");
            user.setPhone(dto.getPhone() != null ? dto.getPhone() : "");

            userDao.save(user);
            return Response.ok().entity("SUCCESS").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
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
