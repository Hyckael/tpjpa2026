package user.entity;

import jakarta.persistence.*;
import event.entity.Event;

import java.util.*;

@Entity
public class Organizer extends User {
    private String companyName;
    public Organizer() {}
    @ManyToMany
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
                super.toString() +
                "companyName='" + companyName + '\'' +
                ", events=" + events +
                '}';
    }
}
