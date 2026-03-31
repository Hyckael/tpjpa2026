package rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import user.dao.UserDao;
import user.entity.User;

import java.util.List;

@Path("user")
@Produces({"application/json", "application/xml"})
public class UserResource {

    private final UserDao userDao = new UserDao();

    @GET
    @Path("/{userId}")
    public User getUserByID(@PathParam("userId") Long userId) {
        User usr = userDao.findOne(userId);
        if (usr == null) {
            throw new WebApplicationException("Aucun utilisateur trouvé", Response.Status.NOT_FOUND);
        }
        return usr;
    }

    @GET
    @Path("/")
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
