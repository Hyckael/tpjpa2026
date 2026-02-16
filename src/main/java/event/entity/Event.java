package event.entity;

import artist.Artist;
import jakarta.persistence.*;
import ticket.entity.Ticket;
import user.entity.Organizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String address;
    private String city;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToMany(mappedBy = "events")
    private List<Organizer> organizer = new ArrayList<Organizer>();
    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    private List<Ticket> tickets = new ArrayList<Ticket>();
    @ManyToMany
    private List<Artist> artists=new ArrayList<Artist>();


    public Event() {}

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
