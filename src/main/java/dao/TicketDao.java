package dao;

import event.Ticket;

public class TicketDao extends AbstractJpaDao <Long, Ticket>{
    public TicketDao() {
        this.setClazz(Ticket.class);
    }
}
