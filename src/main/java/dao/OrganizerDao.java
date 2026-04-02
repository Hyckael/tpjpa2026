package dao;

import daoGeneric.AbstractJpaDao;
import entity.Organizer;

public class OrganizerDao extends AbstractJpaDao<Long, Organizer> {
    public OrganizerDao() {
        this.setClazz(Organizer.class);
    }
}
