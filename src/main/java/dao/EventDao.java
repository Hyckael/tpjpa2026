package dao;

import daoGeneric.AbstractJpaDao;
import entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class EventDao extends AbstractJpaDao<Long, Event> {
    public EventDao() {
        this.setClazz(Event.class);
    }

    //Fonction pour rechercher les ticket.event par organisateurs

    public List<Event> findByOrganizer(Long organizerId) {
        EntityManager em = getEm();
        try{
            return em.createQuery(
                    "SELECT e FROM Event e WHERE e.organizer.id = :id", Event.class
            ).setParameter("id", organizerId).getResultList();
        }catch(NoResultException e){return null;}
        finally{
            em.close();
        }
    }

    //Fonction pour afficher les ticket.event par ville
    public List<Event> findByCity(String city) {
        EntityManager em = getEm();
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Event> cq = cb.createQuery(Event.class);
            Root<Event> root = cq.from(Event.class);

            cq.where(cb.equal(root.get("city"), city));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally{em.close();}
    }

    //Fonction pour determiner les événements liés à un artist
    public List<Event> findByArtisteName(String artisteName) {
        EntityManager em = getEm();
        try{
            return em.createNamedQuery(
                    "Event.findByArtisteName", Event.class
            ).setParameter("name", "%"+artisteName+"%").getResultList();
        }catch(NoResultException e){return null;}
        finally{em.close();}
    }
}
