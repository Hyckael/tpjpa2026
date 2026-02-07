package dao;

import user.Admin;

public class AdminDao extends AbstractJpaDao{
    public AdminDao() {
        setClazz(Admin.class);
    }
}
