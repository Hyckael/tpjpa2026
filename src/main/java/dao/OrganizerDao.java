package dao;

import user.Organizer;

public class OrganizerDao extends AbstractJpaDao<Long, Organizer>{
    public OrganizerDao() {
        this.setClazz(Organizer.class);
    }
}
