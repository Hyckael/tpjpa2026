package user.dao;

import daoGeneric.AbstractJpaDao;
import user.entity.Admin;

public class AdminDao extends AbstractJpaDao<Long, Admin> {
    public AdminDao() {
        this.setClazz(Admin.class);
    }
}