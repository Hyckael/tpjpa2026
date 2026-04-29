package rest;

import dao.TicketDao;
import dto.TicketDto;
import dto.TicketPurchaseDto;
import entity.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("ticket")
@Produces({"application/json", "application/xml"})
@Tag(name = "Tickets", description = "Gestion des tickets")
public class TicketResource {

    private final TicketDao ticketDao = new TicketDao();

    @GET
    @Path("/{ticketId}")
    @Operation(summary = "Récupérer un ticket par son ID")
    @ApiResponse(responseCode = "200", description = "Ticket trouvé",
            content = @Content(schema = @Schema(implementation = TicketDto.class)))
    @ApiResponse(responseCode = "404", description = "Ticket non trouvé")
    public Response getTicketById(@PathParam("ticketId") Long ticketId) {
        try {
            TicketDto ticket = ticketDao.findOneAsDto(ticketId);
            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Ticket non trouvé")
                        .build();
            }
            return Response.ok(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/")
    @Operation(summary = "Récupérer tous les tickets")
    @ApiResponse(responseCode = "200", description = "Liste des tickets")
    public Response getAllTickets() {
        try {
            List<Ticket> tickets = ticketDao.findAll();
            return Response.ok(tickets).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/client/{clientId}")
    @Operation(summary = "Récupérer les tickets d'un client")
    @ApiResponse(responseCode = "200", description = "Liste des tickets du client")
    @ApiResponse(responseCode = "404", description = "Client non trouvé")
    public Response getTicketsByClient(@PathParam("clientId") Long clientId) {
        try {
            List<TicketDto> tickets = ticketDao.findByClient(clientId);
            return Response.ok(tickets).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/event/{eventId}")
    @Operation(summary = "Récupérer les tickets d'un événement")
    @ApiResponse(responseCode = "200", description = "Liste des tickets de l'événement")
    @ApiResponse(responseCode = "404", description = "Événement non trouvé")
    public Response getTicketsByEvent(@PathParam("eventId") Long eventId) {
        try {
            List<TicketDto> tickets = ticketDao.findByEvent(eventId);
            return Response.ok(tickets).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/purchase")
    @Consumes("application/json")
    @Operation(summary = "Acheter un ticket pour un événement")
    @ApiResponse(responseCode = "201", description = "Ticket acheté avec succès",
            content = @Content(schema = @Schema(implementation = TicketDto.class)))
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @ApiResponse(responseCode = "404", description = "Événement ou client non trouvé")
    @ApiResponse(responseCode = "409", description = "Aucun ticket disponible")
    public Response purchaseTicket(TicketPurchaseDto purchaseDto) {
        try {
            if (purchaseDto.getEventId() == null || purchaseDto.getClientId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("eventId et clientId sont requis")
                        .build();
            }

            Ticket ticket = ticketDao.purchaseTicket(
                    purchaseDto.getEventId(),
                    purchaseDto.getClientId()
            );

            // Recharger le ticket avec les détails
            TicketDto ticketDto = ticketDao.findOneAsDto(ticket.getId());

            return Response.status(Response.Status.CREATED)
                    .entity(ticketDto)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Erreur lors de l'achat du ticket: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{ticketId}")
    @Operation(summary = "Supprimer un ticket")
    @ApiResponse(responseCode = "200", description = "Ticket supprimé")
    @ApiResponse(responseCode = "404", description = "Ticket non trouvé")
    public Response deleteTicket(@PathParam("ticketId") Long ticketId) {
        try {
            Ticket ticket = ticketDao.findOne(ticketId);
            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Ticket non trouvé")
                        .build();
            }
            ticketDao.delete(ticket);
            return Response.ok()
                    .entity("Ticket supprimé avec succès")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
