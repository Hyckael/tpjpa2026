package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dto.EventDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQuery(
        name = "findByArtisteName",
        query = "SELECT e FROM Event e JOIN e.artists a WHERE a.name LIKE :name"
)
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int place;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String description;
    private float price;
    @Column(length = 500)
    private String imageUrl;
    private String address;
    private String city;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Date createdAt;
    @ManyToMany(mappedBy = "events")
    @JsonIgnore
    private List<Organizer> organizer = new ArrayList<Organizer>();
    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<Ticket>();
    @ManyToMany
    @JsonIgnore
    private List<Artist> artists=new ArrayList<Artist>();


    public Event(){}
    public Event(EventDTO eventDTO) {
        this.address = eventDTO.getAddress();
        this.city = eventDTO.getCity();
        this.description = eventDTO.getDescription();
        this.price = eventDTO.getPrice();
        this.place = eventDTO.getPlace();
        this.date = eventDTO.getDate();
        this.imageUrl = eventDTO.getImageUrl();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlace() {
        return place;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setPlace(int place) {
        this.place = place;
    }

    public String getCity(){return city;}

    public void setCity(String city){this.city = city;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Organizer> getOrganizer() {
        return organizer;
    }

    public void setOrganizer(List<Organizer> organizer) {
        this.organizer = organizer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtist(List<Artist> artists) {
        this.artists = artists;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", tickets=" + tickets +
                ",artists=" + artists +
                ", createdAt=" + createdAt +
                '}';
    }
}
