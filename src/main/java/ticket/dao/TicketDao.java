package ticket.dao;

import daoGeneric.AbstractJpaDao;
import ticket.entity.Ticket;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        this.setClazz(Ticket.class);
    }
}
