package dto;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.Artist;
import entity.Event;
import entity.Organizer;

public class EventDTO {
    private Long id;
    private int place;
    private String description;
    private String address;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date date;
    private String city;
    private float price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Date createdAt;
    private List<String> artisteName;
    private List<String> organizerName;
    private int ticketCount;
    private Long organizerId;

    public EventDTO() {}

    public EventDTO(Event event){
        this.id = event.getId();
        this.place = event.getPlace();
        this.date =  event.getDate();
        this.description = event.getDescription();
        this.address = event.getAddress();
        this.price = event.getPrice();
        this.imageUrl = event.getImageUrl();
        this.city = event.getCity();
        this.ticketCount = event.getTickets().size();
        this.createdAt = event.getCreatedAt();
        this.artisteName = event.getArtists().stream().map(Artist::getName).collect(Collectors.toList());
        this.organizerName = event.getOrganizer().stream().map(Organizer::getName).collect(Collectors.toList());
        if (!event.getOrganizer().isEmpty()) {
            this.organizerId = event.getOrganizer().get(0).getId();
        }
    }

    public Long getId() {
        return id;
    }

    public int getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Date getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public float getPrice() {
        return price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<String> getArtisteName() {
        return artisteName;
    }

    public List<String> getOrganizerName() {
        return organizerName;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setArtisteName(List<String> artisteName) {
        this.artisteName = artisteName;
    }

    public void setOrganizerName(List<String> organizerName) {
        this.organizerName = organizerName;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
