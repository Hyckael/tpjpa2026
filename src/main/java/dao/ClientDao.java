package dao;

import daoGeneric.AbstractJpaDao;
import entity.Client;

public class ClientDao extends AbstractJpaDao<Long, Client> {
    public ClientDao() {
        this.setClazz(Client.class);
    }
}
