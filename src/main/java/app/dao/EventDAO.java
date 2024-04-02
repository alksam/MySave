package app.dao;

import app.model.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EventDAO {
    private static EventDAO instance;
    private static EntityManagerFactory emf;
public EventDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    public static EventDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EventDAO(emf);
        }
        return instance;
    }

    public EventDAO() {
    }
    // As a user, I want to see all the events/workshops that are going to be held.

    public Event getAlleEvents( Event event) {
        EntityManager em = emf.createEntityManager();
        return em.find(Event.class, event);

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
}
