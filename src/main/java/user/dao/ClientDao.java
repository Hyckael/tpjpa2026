package user.dao;

import daoGeneric.AbstractJpaDao;
import user.entity.Client;

public class ClientDao extends AbstractJpaDao<Long, Client> {
    public ClientDao() {
        this.setClazz(Client.class);
    }
}
