package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.Ticket;
import entity.TicketStatus;

import java.util.Date;

public class TicketDto {
    private Long id;
    private String eventImageUrl;
    private String eventAddress;
    private String eventCity;
    private float eventPrice;
    private Date eventDate;
    private String number;
    private TicketStatus status;
    private Long clientId;
    private String clientName;
    private Long eventId;
    private String eventDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Date purchaseDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Date createdAt;

    public TicketDto() {}

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.number = ticket.getNumber();
        this.status = ticket.getStatus();
        this.purchaseDate = ticket.getPurchaseDate();
        this.createdAt = ticket.getCreatedAt();

        if (ticket.getClient() != null) {
            this.clientId = ticket.getClient().getId();
            this.clientName = ticket.getClient().getName();
        }

        if (ticket.getEvent() != null) {
            this.eventId = ticket.getEvent().getId();
            this.eventDescription = ticket.getEvent().getDescription();
        }
        if (ticket.getEvent() != null) {
            this.eventId = ticket.getEvent().getId();
            this.eventDescription = ticket.getEvent().getDescription();
            this.eventImageUrl = ticket.getEvent().getImageUrl();   // ← ajout
            this.eventAddress = ticket.getEvent().getAddress();     // ← ajout
            this.eventCity = ticket.getEvent().getCity();           // ← ajout
            this.eventPrice = ticket.getEvent().getPrice();         // ← ajout
            this.eventDate = ticket.getEvent().getDate();           // ← ajout
        }
    }

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

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
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

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public float getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(float eventPrice) {
        this.eventPrice = eventPrice;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
