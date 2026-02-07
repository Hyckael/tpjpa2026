package user;

import auth.User;
import event.Ticket;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {
    @Temporal(TemporalType.TIMESTAMP)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Ticket> ticket = new ArrayList<Ticket>();

    public Customer() {}

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "createdAt=" +
                ", ticket=" + ticket +
                '}';
    }
}
