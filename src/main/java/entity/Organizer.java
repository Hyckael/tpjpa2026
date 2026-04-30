package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Organizer extends User {
    private String companyName;
    public Organizer() {}
    @ManyToMany
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
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
                super.toString() +
                "companyName='" + companyName + '\'' +
                ", events=" + events +
                '}';
    }
}
