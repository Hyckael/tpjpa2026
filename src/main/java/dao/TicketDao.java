package dao;

import event.Ticket;

public class TicketDao extends AbstractJpaDao {
    public TicketDao() {
        setClazz(Ticket.class);
    }
}
