package user.entity;

import jakarta.persistence.*;
import ticket.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {
    @Temporal(TemporalType.TIMESTAMP)
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Ticket> ticket = new ArrayList<Ticket>();
    private String city;

    public Client() {}

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    @Override
    public String toString() {
        return "Client{" +
                super.toString() +
                ", ticket=" + ticket +
                '}';
    }
}
