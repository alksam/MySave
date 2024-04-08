package app.dao;

import app.model.Event;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    public void addUserToEvent(User user, Event createdEvent) {
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

    public List<User> getUsersForEvent(Event event) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT u FROM User u JOIN u.events e WHERE e.id = :eventId", User.class)
                .setParameter("eventId", event.getEventId())
                .getResultList();
    }

    public List<User> getAllRegistereUsers(int eventId) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT u FROM User u JOIN u.events e WHERE e.id = :eventId", User.class)
                .setParameter("eventId", eventId)
                .getResultList();


    }

    public void addUserToEvent(int userId, int eventId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, userId);
            Event event = em.find(Event.class, eventId);

            if (user != null && event != null) {
                // Add the user to the event
                event.getUsers().add(user);

                // Also update the user's side of the relationship if it's bidirectional
                user.getEvents().add(event);
                em.merge(user);
                em.merge(event);
            } else {
                System.out.println("User or event not found");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error adding user to event", e);
        } finally {
            em.close();
        }
    }

}
