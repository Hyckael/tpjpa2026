package dao;

import event.Event;
import user.Organizer;

import java.util.List;

public class EventDao extends AbstractJpaDao {
    public EventDao() {
        setClazz(Event.class);
    }

    //Créer une fonction pour rechercher les event par organisateurs
}
