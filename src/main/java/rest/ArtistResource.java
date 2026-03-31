package rest;

import artist.Artist;
import artist.ArtistDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import user.entity.User;

import java.util.List;

@Path("artist")
@Produces({"application/json", "application/xml"})
public class ArtistResource {
    public final ArtistDao  artistDao =  new ArtistDao();

    @GET
    @Path("/{artistId}")
    public Artist getArtistByID(@PathParam("artistId") Long artistId) {
        Artist artist = artistDao.findOne(artistId);
        if (artist == null) {
            throw new WebApplicationException("Aucun artist trouvé", Response.Status.NOT_FOUND);
        }
        return artist;
    }

    @GET
    @Path("/")
    public List<Artist> getAllArtist() {
        List<Artist> artists = artistDao.findAll();

        if (artists == null) {
            throw new WebApplicationException("Aucun artist trouvé", Response.Status.NOT_FOUND);
        }
        return artists;
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    public Response addArtist (Artist artist) {
        try {
            artistDao.save(artist);
            return Response.ok().entity("SUCCESS").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{artistId}")
    public Response deleteArtist(@PathParam("artistId") Long artistId) {
        try {
            Artist artist = artistDao.findOne(artistId);
            if (artist == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Artist non trouvé")
                        .build();
            }
            artistDao.delete(artist);
            return Response.ok()
                    .entity("artist supprimé")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }
}
