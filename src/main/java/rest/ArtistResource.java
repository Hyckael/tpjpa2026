package rest;

import dto.ArtistDto;
import entity.Artist;
import dao.ArtistDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("artist")
@Produces({"application/json", "application/xml"})
@Tag(name = "Artists", description = "Gestion des Artists")
public class ArtistResource {
    public final ArtistDao artistDao = new ArtistDao();

    @GET
    @Path("/{artistId}")
    @Operation(summary = "Récupérer un artist par son Id")
    @ApiResponse(responseCode = "200", description = "artist trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public Artist getArtistByID(@PathParam("artistId") Long artistId) {
        Artist artist = artistDao.findOne(artistId);
        if (artist == null) {
            throw new WebApplicationException("Aucun artist trouvé", Response.Status.NOT_FOUND);
        }
        return artist;
    }

    @GET
    @Path("/")
    @Operation(summary = "Récupérer tous les artists")
    @ApiResponse(responseCode = "200", description = "artists trouvés")
    @ApiResponse(responseCode = "404", description = "Introuvable")
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
    @Operation(summary = "Création d'un artist lié à un événement")
    @ApiResponse(responseCode = "200", description = "artist créé")
    @ApiResponse(responseCode = "404", description = "échec")
    public Response addArtist(ArtistDto dto) { // ← ArtistDto au lieu de Artist
        try {
            artistDao.saveAndLinkToEvent(dto);
            return Response.ok().entity("SUCCESS").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{artistId}")
    @Operation(summary = "Suppression d'un artist")
    @ApiResponse(responseCode = "200", description = "artist supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public Response deleteArtist(@PathParam("artistId") Long artistId) {
        try {
            Artist artist = artistDao.findOne(artistId);
            if (artist == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Artist non trouvé")
                        .build();
            }
            artistDao.delete(artist);
            return Response.ok().entity("artist supprimé").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}