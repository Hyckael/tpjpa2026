package dto;

public class TicketPurchaseDto {
    private Long eventId;
    private Long clientId;

    public TicketPurchaseDto() {}

    public TicketPurchaseDto(Long eventId, Long clientId) {
        this.eventId = eventId;
        this.clientId = clientId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
