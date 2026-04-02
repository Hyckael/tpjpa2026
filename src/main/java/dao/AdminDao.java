package dao;

import daoGeneric.AbstractJpaDao;
import entity.Admin;

public class AdminDao extends AbstractJpaDao<Long, Admin> {
    public AdminDao() {
        this.setClazz(Admin.class);
    }
}