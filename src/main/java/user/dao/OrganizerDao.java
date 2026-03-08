package user.dao;

import daoGeneric.AbstractJpaDao;
import user.entity.Organizer;

public class OrganizerDao extends AbstractJpaDao<Long, Organizer> {
    public OrganizerDao() {
        this.setClazz(Organizer.class);
    }
}
