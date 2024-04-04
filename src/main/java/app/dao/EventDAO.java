package app.dao;

import app.model.Event;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class EventDAO {
    private static EventDAO instance;
    private static EntityManagerFactory emf;
public EventDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static EventDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            EventDAO.emf = emf;
            instance = new EventDAO(EventDAO.emf);
        }
        return instance;
    }

    public EventDAO() {
    }
    // As a user, I want to see all the events/workshops that are going to be held.

    public List<Event> getAlleEvents() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();

    }

    public Event getEventById(int id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Event.class, id);
    }





    public Event create(Event event) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(event);
        em.getTransaction().commit();
        return event;
    }
    public Event read(int id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Event.class, id);
    }


    public Event update(Event event) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(event);
        em.getTransaction().commit();
        return event;
    }


    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event event = em.find(Event.class, id);
        em.remove(event);
        em.getTransaction().commit();
    }

    public void addUserToEvent(User user, int createdEvent) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event event = em.find(Event.class, createdEvent.getEventId());
        event.addUser(user);
        em.getTransaction().commit();
    }

    public void removeUserFromEvent(User user, Event createdEvent) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event event = em.find(Event.class, createdEvent.getEventId());
        event.removeUser(user);
        em.getTransaction().commit();
    }

    public User getUsersForEvent(Event event , User user) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT u FROM User u JOIN u.events e WHERE e.EventId = :eventId", User.class)
                .setParameter("eventId", event.getEventId())
                .getSingleResult();
    }

    public User getAllRegistereUsers(User user, Event event) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT u FROM User u JOIN u.events e WHERE e.EventId = :eventId", User.class)
                .setParameter("eventId", event.getEventId())
                .getSingleResult();


    }

}
