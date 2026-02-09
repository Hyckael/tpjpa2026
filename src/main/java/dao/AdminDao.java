package dao;

import user.Admin;

public class AdminDao extends AbstractJpaDao<Long, Admin> {
    public AdminDao() {
        this.setClazz(Admin.class);
    }
}
