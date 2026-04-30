package dao;

import daoGeneric.AbstractJpaDao;
import dto.TicketDto;
import entity.*;
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

            // Vérifie que l'event existe
            Event event = em.find(Event.class, eventId);
            if (event == null) {
                throw new IllegalArgumentException("Événement non trouvé");
            }

            // Vérifie que le client existe
            User user = em.find(User.class, clientId);
            if (!(user instanceof Client)) {
                throw new IllegalArgumentException("Client non trouvé");
            }
            Client client = (Client) user;

            // Compte les tickets déjà vendus pour cet event
            Long soldCount = em.createQuery(
                            "SELECT COUNT(t) FROM Ticket t WHERE t.event.id = :eventId " +
                                    "AND t.status = :status", Long.class
                    ).setParameter("eventId", eventId)
                    .setParameter("status", TicketStatus.SOLD)
                    .getSingleResult();

            // Vérifie qu'il reste des places
            if (soldCount >= event.getPlace()) {
                throw new IllegalStateException("Aucune place disponible pour cet événement");
            }

            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setClient(client);
            ticket.setStatus(TicketStatus.SOLD);
            ticket.setPurchaseDate(new Date());
            ticket.setNumber("TKT-" + eventId + "-" + clientId + "-" + System.currentTimeMillis());

            em.persist(ticket);
            tx.commit();
            return ticket;

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
