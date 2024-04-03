package app.handler;

import app.model.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;



public class EventHandler implements IEventHandler{

    private EntityManagerFactory emf;

    public EventHandler(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EventHandler() {

    }

    @Override
    public List<Event> getAllEvents() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Event> events = em.createQuery("SELECT e FROM Event e", Event.class).getResultList();

            return events;
        } finally {
            em.close();
        }
    }


    @Override
    public Optional<Event> getEventById(int id) {
        return Optional.empty();
    }

    @Override
    public Event createEvent(Event event) {
        return null;
    }

    @Override
    public Event updateEvent(int id, Event event) {
        return null;
    }

    @Override
    public void deleteEvent(int id) {

    }
}
