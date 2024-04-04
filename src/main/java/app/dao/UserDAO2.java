package app.dao;

import app.model.Event;
import app.model.Role;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO2 {


    private static UserDAO2 instance;
    private static EntityManagerFactory emf;
    public UserDAO2(EntityManagerFactory emf) {
        this.emf = emf;
    }

   public static UserDAO2 getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            UserDAO2.emf = emf;
            instance = new UserDAO2(UserDAO2.emf);
        }
        return instance;
    }

    public UserDAO2() {
    }
    // As a user, I want to see all the events/workshops that are going to be held.

    public List<User> getAlleUser() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();

    }

    public User getUserById(int id) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, id);
    }

    public User create(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }



    public User read(int id) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, id);
    }


    public User update(User user){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        return user;
    }



    public void deleteUser(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        em.remove(user);
        em.getTransaction().commit();
    }
    public void AddEventToUser(int userId, int eventId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, userId);
        Event event = em.find(Event.class, eventId);
        user.addEvent(event);
        em.getTransaction().commit();
    }
}

