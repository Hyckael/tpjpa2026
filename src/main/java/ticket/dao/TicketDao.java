package ticket.dao;

import dao.AbstractJpaDao;
import ticket.entity.Ticket;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        this.setClazz(Ticket.class);
    }
}
