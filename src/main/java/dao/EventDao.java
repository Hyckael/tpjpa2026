package dao;

import daoGeneric.AbstractJpaDao;
import dto.EventDTO;
import entity.Event;
import entity.Organizer;
import entity.TicketStatus;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class EventDao extends AbstractJpaDao<Long, Event> {
    public EventDao() {
        this.setClazz(Event.class);
    }

    // Méthode utilitaire
    private EventDTO toDTO(Event event) {
        event.getTickets().size();
        event.getArtists().size();
        event.getOrganizer().size();
        return new EventDTO(event);
    }

    public Event saveWithOrganizer(Event event, Long organizerId) {
        EntityManager em = getEm();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (organizerId != null) {
                User user = em.find(User.class, organizerId);
                if (user instanceof Organizer) {
                    Organizer organizer = (Organizer) user;
                    event.getOrganizer().add(organizer);
                    organizer.getEvents().add(event);
                }
            }
            em.persist(event);
            tx.commit();
            return event;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // détails ───────────────────────────────────────────────
    public EventDTO findOneWithDetails(Long id) {
        EntityManager em = getEm();
        try {
            Event event = em.find(Event.class, id);
            if (event == null) return null;
            return toDTO(event); // ← session encore ouverte
        } finally {
            em.close();
        }
    }

    public List<EventDTO> findAllWithDetails() {
        EntityManager em = getEm();
        try {
            List<Event> events = em.createQuery(
                    "SELECT DISTINCT e FROM Event e " +
                            "LEFT JOIN FETCH e.tickets " +
                            "LEFT JOIN FETCH e.artists " +
                            "LEFT JOIN FETCH e.organizer",
                    Event.class
            ).getResultList();

            List<EventDTO> dtos = new ArrayList<>();
            for (Event event : events) {
                dtos.add(toDTO(event));
            }
            return dtos;
        } finally {
            em.close();
        }
    }
    public long countAvailableTickets(Long eventId) {
        EntityManager em = getEm();
        try {
            Event event = em.find(Event.class, eventId);
            if (event == null) return -1; // -1 = event non trouvé

            return event.getTickets()
                    .stream()
                    .filter(t -> t.getStatus() == TicketStatus.AVAILABLE)
                    .count();
        } finally {
            em.close();
        }
    }
    public List<EventDTO> findByOrganizerAsDTO(Long organizerId) {
        EntityManager em = getEm();
        try {
            List<Event> events = em.createQuery(
                    "SELECT DISTINCT e FROM Event e JOIN e.organizer o WHERE o.id = :id",
                    Event.class
            ).setParameter("id", organizerId).getResultList();

            List<EventDTO> dtos = new ArrayList<>();
            for (Event event : events) {
                dtos.add(toDTO(event)); // ← session encore ouverte
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<EventDTO> findByCityAsDTO(String city) {
        EntityManager em = getEm();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Event> cq = cb.createQuery(Event.class);
            Root<Event> root = cq.from(Event.class);
            cq.where(cb.equal(root.get("city"), city));
            List<Event> events = em.createQuery(cq).getResultList();

            List<EventDTO> dtos = new ArrayList<>();
            for (Event event : events) {
                dtos.add(toDTO(event));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<EventDTO> findByArtisteNameAsDTO(String artisteName) {
        EntityManager em = getEm();
        try {
            List<Event> events = em.createNamedQuery("findByArtisteName", Event.class)
                    .setParameter("name", "%" + artisteName + "%")
                    .getResultList();

            List<EventDTO> dtos = new ArrayList<>();
            for (Event event : events) {
                dtos.add(toDTO(event));
            }
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}