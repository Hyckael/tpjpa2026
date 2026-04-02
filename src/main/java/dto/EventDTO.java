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
    private String adress;
    private Date date;
    private String city;
    private float price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Date createdAt;
    private List<String> artisteName;
    private List<String> organizerName;
    private int ticketCount;

    public EventDTO() {}

    public EventDTO(Event event){
        this.id = event.getId();
        this.place = event.getPlace();
        this.description = event.getDescription();
        this.adress = event.getAddress();
        this.price = event.getPrice();
        this.city = event.getCity();
        this.ticketCount = event.getTickets().size();
        this.createdAt = event.getCreatedAt();
        this.artisteName = event.getArtists().stream().map(Artist::getName).collect(Collectors.toList());
        this.organizerName = event.getOrganizer().stream().map(Organizer::getName).collect(Collectors.toList());
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

    public String getAdress() {
        return adress;
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

    public void setAdress(String adress) {
        this.adress = adress;
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
}
