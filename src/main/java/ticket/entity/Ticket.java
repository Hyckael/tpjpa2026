package ticket.entity;

import event.entity.Event;
import jakarta.persistence.*;
import user.entity.Client;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Event event;
    @Temporal(TemporalType.TIMESTAMP)
    private  Date purchaseDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Ticket() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", status=" + status +
                ", customer=" + client +
                ", event=" + event +
                ", purchaseDate=" + purchaseDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
