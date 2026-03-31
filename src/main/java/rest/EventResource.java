package rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import event.EventDao;
import event.Event;
import ticket.TicketDao;
import ticket.TicketStatus;

import java.util.List;
import java.util.stream.Collectors;

@Path("event")
@Produces({"application/json", "application/xml"})
public class EventResource {

    private final EventDao eventDao = new EventDao();
    private final TicketDao ticketDao = new TicketDao();

    @GET
    @Path("/{eventId}")
    public Event getEventById(@PathParam("eventId") Long eventId) {
        Event event = eventDao.findOne(eventId);
        if (event == null) {
            throw new WebApplicationException("Événement non trouvé", Response.Status.NOT_FOUND);
        }
        return new Event(event);
    }

    @GET
    @Path("/")
    public List<Event> getAllEvents() {
        return eventDao.findAll().stream()
                .map(Event::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/organizer/{organizerId}")
    public List<Event> getEventsByOrganizer(@PathParam("organizerId") Long organizerId) {
        return eventDao.findByOrganizer(organizerId).stream()
                .map(Event::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/city/{city}")
    public List<Event> getEventsByCity(@PathParam("city") String city) {
        return eventDao.findByCity(city).stream()
                .map(Event::new)
                .collect(Collectors.toList());
    }

    @POST
    public Response createEvent(Event event) {
        try {
            eventDao.save(event);
            return Response.status(Response.Status.CREATED)
                    .entity(new Event(event)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{eventId}/available-tickets")
    public Response getAvailableTickets(@PathParam("eventId") Long eventId) {
        Event event = eventDao.findOne(eventId);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Événement non trouvé").build();
        }
        return Response.ok(event.getTickets().stream()
                .filter(t -> t.getStatus() == TicketStatus.AVAILABLE)
                .count()).build();
    }

    @DELETE
    @Path("/{eventId}")
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