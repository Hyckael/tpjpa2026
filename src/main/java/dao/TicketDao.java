package dao;

import daoGeneric.AbstractJpaDao;
import entity.Ticket;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        this.setClazz(Ticket.class);
    }
}
