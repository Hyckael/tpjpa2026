package rest;

import dto.EventDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import dao.EventDao;
import entity.Event;
import dao.TicketDao;
import entity.TicketStatus;

import java.util.List;
import java.util.stream.Collectors;

@Path("event")
@Produces({"application/json", "application/xml"})
@Tag(name = "Événements", description = "Gestion des événements")
public class EventResource {

    private final EventDao eventDao = new EventDao();
    private final TicketDao ticketDao = new TicketDao();

    @GET
    @Path("/{eventId}")
    @Operation(summary = "Récupérer un événement par son Id")
    @ApiResponse(responseCode = "200", description = "Événement trouvé",
            content = @Content(schema = @Schema(implementation = EventDTO.class)))
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public EventDTO getEventById(@PathParam("eventId") Long eventId) {
        Event event = eventDao.findOneWithDetails(eventId);
        if (event == null) {
            throw new WebApplicationException("Événement non trouvé", Response.Status.NOT_FOUND);
        }
        return new EventDTO(event);
    }

    @GET
    @Path("/")
    @Operation(summary = "Récupérer un tous les événements")
    @ApiResponse(responseCode = "200", description = "Événement trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public List<EventDTO> getAllEvents() {
        return eventDao.findAllWithDetails().stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/organizer/{organizerId}")
    @Operation(summary = "Récupérer un événement par ses organisateurs")
    @ApiResponse(responseCode = "200", description = "Événement trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public List<EventDTO> getEventsByOrganizer(@PathParam("organizerId") Long organizerId) {
        List<Event> events = eventDao.findByOrganizer(organizerId);
        if (events == null) return List.of();
        return events.stream().map(EventDTO::new).collect(Collectors.toList());
    }

    @GET
    @Path("/city/{city}")
    @Operation(summary = "Recherche un évènement à partir d'une ville")
    @ApiResponse(responseCode = "200", description = "Événement trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public List<EventDTO> getEventsByCity(@PathParam("city") String city) {
        return eventDao.findByCity(city).stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    @POST
    @Path("/create")
    @Operation(summary = "création d'un évenement")
    @ApiResponse(responseCode = "200", description = "Événement créé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public Response createEvent(EventDTO eventDTO) {
        try {
            Event event = new Event(eventDTO);
            eventDao.saveWithOrganizer(event, eventDTO.getOrganizerId()); // ← nouvelle méthode
            return Response.status(Response.Status.CREATED)
                    .entity(new EventDTO(event)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{eventId}/available-tickets")
    @Operation(summary = "Récupérer un événement dont les tickets sont disponible")
    @ApiResponse(responseCode = "200", description = "Événement trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public Response getAvailableTickets(@PathParam("eventId") Long eventId) {
        Event event = eventDao.findOneWithDetails(eventId);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Événement non trouvé").build();
        }
        long availableCount = event.getTickets()
                .stream()
                .filter(t -> t.getStatus() == TicketStatus.AVAILABLE)
                .count();
        return Response.ok(availableCount).build();
    }

    @GET
    @Path("/search")
    @Operation(summary = "Rechercher des événements par artiste")
    @ApiResponse(responseCode = "200", description = "Liste des événements trouvés")
    @ApiResponse(responseCode = "404", description = "Aucun événement trouvé")
    public List<EventDTO> getEventsByArtist(
            @Parameter(description = "Nom de l'artiste", required = true)
            @QueryParam("artist") String artisteName
    ) {
        List<Event> events = eventDao.findByArtisteName(artisteName);

        if (events == null || events.isEmpty()) {
            throw new WebApplicationException("Aucun événement trouvé pour cet artiste",
                    Response.Status.NOT_FOUND);
        }

        return events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/{eventId}")
    @Operation(summary = "Supprimer un événement par ID")
    @ApiResponse(responseCode = "200", description = "Événement trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    public Response deleteEvent(@PathParam("eventId") Long eventId) {
        try {
            Event event = eventDao.findOne(eventId);
            if (event == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Evenement non trouvé").build();
            }
            eventDao.delete(event);
            return Response.ok().entity("Entité supprimé avec succès").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}