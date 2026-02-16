package event.dao;

import dao.AbstractJpaDao;
import event.entity.Event;

public class EventDao extends AbstractJpaDao<Long, Event> {
    public EventDao() {
        this.setClazz(Event.class);
    }

    //Créer une fonction pour rechercher les event par organisateurs

    //Créer une fonction pour afficher les event par ville
}
