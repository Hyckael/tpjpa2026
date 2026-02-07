package event;

import jakarta.persistence.*;
import user.Customer;

import java.util.Date;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @ManyToOne
    private Customer customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
                ", customer=" + customer +
                ", event=" + event +
                ", purchaseDate=" + purchaseDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
