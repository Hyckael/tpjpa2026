package event;

import daoGeneric.AbstractJpaDao;

import java.util.List;

public class EventDao extends AbstractJpaDao<Long, Event> {
    public EventDao() {
        this.setClazz(Event.class);
    }

    //Fonction pour rechercher les ticket.event par organisateurs

    public List<Event> findByOrganizer(Long organizerId) {
        return entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.organizer.id = :id", Event.class
        ).setParameter("id", organizerId).getResultList();
    }


    //Fonction pour afficher les ticket.event par ville
    public List<Event> findByCity(String city) {
        return entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.city = :city", Event.class)
                .setParameter("city", city)
                .getResultList();
    }

    //Fonction pour determiner les evenement liés à un artitist
    public List<Event> findByArtisteName(String artisteName) {
        return entityManager.createQuery(
                "SELECT e FROM Event e JOIN e.artists a WHERE a.name LIKE :name", Event.class
        ).setParameter("name", "%"+artisteName+"%").getResultList();
    }
}
