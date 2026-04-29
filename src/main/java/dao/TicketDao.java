package dao;

import daoGeneric.AbstractJpaDao;
import dto.TicketDto;
import entity.Client;
import entity.Event;
import entity.Ticket;
import entity.TicketStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        this.setClazz(Ticket.class);
    }

    /**
     * Méthode métier pour acheter un ticket
     * Vérifie la disponibilité, crée le ticket et l'associe au client et à l'événement
     */
    public Ticket purchaseTicket(Long eventId, Long clientId) {
        EntityManager em = getEm();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Récupérer l'événement
            Event event = em.find(Event.class, eventId);
            if (event == null) {
                throw new IllegalArgumentException("Événement non trouvé");
            }

            // Récupérer le client
            Client client = em.find(Client.class, clientId);
            if (client == null) {
                throw new IllegalArgumentException("Client non trouvé");
            }

            // Vérifier s'il y a des tickets disponibles
            long availableCount = event.getTickets()
                    .stream()
                    .filter(t -> t.getStatus() == TicketStatus.AVAILABLE)
                    .count();

            if (availableCount == 0) {
                throw new IllegalStateException("Aucun ticket disponible pour cet événement");
            }

            // Trouver un ticket disponible
            Ticket availableTicket = event.getTickets()
                    .stream()
                    .filter(t -> t.getStatus() == TicketStatus.AVAILABLE)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Aucun ticket disponible"));

            // Mettre à jour le ticket
            availableTicket.setStatus(TicketStatus.SOLD);
            availableTicket.setClient(client);
            availableTicket.setPurchaseDate(new Date());

            // Ajouter le ticket à la liste du client
            client.getTicket().add(availableTicket);

            em.merge(availableTicket);
            em.merge(client);

            tx.commit();
            return availableTicket;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Récupère tous les tickets d'un client
     */
    public List<TicketDto> findByClient(Long clientId) {
        EntityManager em = getEm();
        try {
            List<Ticket> tickets = em.createQuery(
                    "SELECT t FROM Ticket t WHERE t.client.id = :clientId", Ticket.class
            ).setParameter("clientId", clientId).getResultList();

            // Force le chargement des relations
            tickets.forEach(t -> {
                t.getClient().getName();
                t.getEvent().getDescription();
            });

            return tickets.stream()
                    .map(TicketDto::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    /**
     * Récupère tous les tickets d'un événement
     */
    public List<TicketDto> findByEvent(Long eventId) {
        EntityManager em = getEm();
        try {
            List<Ticket> tickets = em.createQuery(
                    "SELECT t FROM Ticket t WHERE t.event.id = :eventId", Ticket.class
            ).setParameter("eventId", eventId).getResultList();

            // Force le chargement des relations
            tickets.forEach(t -> {
                if (t.getClient() != null) {
                    t.getClient().getName();
                }
                t.getEvent().getDescription();
            });

            return tickets.stream()
                    .map(TicketDto::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    /**
     * Convertit un ticket en DTO avec ses relations chargées
     */
    public TicketDto findOneAsDto(Long ticketId) {
        EntityManager em = getEm();
        try {
            Ticket ticket = em.find(Ticket.class, ticketId);
            if (ticket == null) return null;

            // Force le chargement des relations
            if (ticket.getClient() != null) {
                ticket.getClient().getName();
            }
            if (ticket.getEvent() != null) {
                ticket.getEvent().getDescription();
            }

            return new TicketDto(ticket);
        } finally {
            em.close();
        }
    }
}
