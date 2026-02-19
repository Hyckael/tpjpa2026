package event.service;

import artist.Artist;
import event.dao.EventDao;
import event.entity.Event;

import java.util.*;

public class EventService {
    EventDao eventDao=new EventDao();
    public Event createEvent(int place, String description, float price, String address, Date date, List<Artist> artists,String city) {
        Event event=new Event();
        event.setPlace(place);
        event.setDescription(description);
        event.setPrice(price);
        event.setAddress(address);
        event.setTickets(new ArrayList<>());
        event.setDate(date);
        event.setCity(city);
        event.setCreatedAt(new Date());
        event.setArtist(artists);

        eventDao.save(event);
        return event;

    }

    public List<Event> getAllEvent() {
        return eventDao.findAll();
    }
}
