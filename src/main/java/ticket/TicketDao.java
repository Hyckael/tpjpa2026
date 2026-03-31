package ticket;

import daoGeneric.AbstractJpaDao;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        this.setClazz(Ticket.class);
    }
}
