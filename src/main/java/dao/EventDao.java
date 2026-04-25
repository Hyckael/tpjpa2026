package dao;

import daoGeneric.AbstractJpaDao;
import entity.Event;
import entity.Organizer;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class EventDao extends AbstractJpaDao<Long, Event> {
    public EventDao() {
        this.setClazz(Event.class);
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

    public Event findOneWithDetails(Long id) {
        EntityManager em = getEm();
        try {
            Event event = em.find(Event.class, id);
            if (event != null) {
                event.getTickets().size();
                event.getArtists().size();
                event.getOrganizer().size();
            }
            return event;
        } finally {
            em.close();
        }
    }

    public List<Event> findAllWithDetails() {
        EntityManager em = getEm();
        try {
            return em.createQuery(
                    "SELECT DISTINCT e FROM Event e " +
                            "LEFT JOIN FETCH e.tickets " +
                            "LEFT JOIN FETCH e.artists " +
                            "LEFT JOIN FETCH e.organizer",
                    Event.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Event> findByOrganizer(Long organizerId) {
        EntityManager em = getEm();
        try {
            return em.createQuery(
                    "SELECT e FROM Event e JOIN e.organizer o WHERE o.id = :id", Event.class
            ).setParameter("id", organizerId).getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Event> findByCity(String city) {
        EntityManager em = getEm();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Event> cq = cb.createQuery(Event.class);
            Root<Event> root = cq.from(Event.class);
            cq.where(cb.equal(root.get("city"), city));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public List<Event> findByArtisteName(String artisteName) {
        EntityManager em = getEm();
        try {
            return em.createNamedQuery("findByArtisteName", Event.class)
                    .setParameter("name", "%" + artisteName + "%")
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}