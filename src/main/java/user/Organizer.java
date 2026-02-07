package user;

import auth.User;
import event.Event;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Organizer extends User {
    private String companyName;
    public Organizer() {}
//    @OneToMany(mappedBy = "organizer", cascade = CascadeType.PERSIST)
    @ManyToMany
    @JoinTable(
            name = "event_organizer",
            joinColumns = @JoinColumn(name = "organizer_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "companyName='" + companyName + '\'' +
                ", events=" + events +
                '}';
    }
}
