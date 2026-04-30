package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client extends User {
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Ticket> ticket = new HashSet<>();
    private String city;

    public Client() {}

    public Set<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(Set<Ticket> ticket) {
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
