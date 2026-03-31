package ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import event.Event;
import jakarta.persistence.*;
import user.entity.Client;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Event event;
    @Temporal(TemporalType.TIMESTAMP)
    private  Date purchaseDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Date createdAt;

    public Ticket() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", numero='" + number + '\'' +
                ", status=" + status +
                ", customer=" + client +
                ", ticket.event=" + event +
                ", purchaseDate=" + purchaseDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
