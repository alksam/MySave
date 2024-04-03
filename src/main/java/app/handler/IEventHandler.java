package app.handler;

import app.model.Event;
import java.util.List;
import java.util.Optional;

public interface IEventHandler {
    List<Event> getAllEvents();

    Optional<Event> getEventById(int id);

    Event createEvent(Event event);

    Event updateEvent(int id, Event event);

    void deleteEvent(int id);
}
