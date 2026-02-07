package dao;

import user.Organizer;

public class OrganizerDao extends AbstractJpaDao{
    public OrganizerDao() {
        setClazz(Organizer.class);
    }
}
