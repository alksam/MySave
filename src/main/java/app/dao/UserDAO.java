package app.dao;

import app.config.HibernateConfig;
import app.exceptions.EntityNotFoundException;
import app.model.Event;
import app.model.Role;
import app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO implements ISecurityDAO{
    private EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public User createUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = new User(username, password);
        Role userRole = em.find(Role.class, "user");
        if (userRole == null) {
            userRole = new Role("user");
            em.persist(userRole);
        }
        user.addRole(userRole);
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

public User UpdateUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = new User(username, password);
        Role userRole = em.find(Role.class, "user");
        if (userRole == null) {
            userRole = new Role("user");
            em.persist(userRole);
        }
        user.addRole(userRole);
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public List<User> getAlleUser() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT e FROM User e", User.class).getResultList();


    }

    public User getUserById(int id) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, id);
    }



    public User verifyUser(String name, String password) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, name);
        if (user == null)
            throw new EntityNotFoundException("No user found with name: " + name);
        if (!user.verifyUser(password))
            throw new EntityNotFoundException("Wrong password");
        return user;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        UserDAO dao = new UserDAO(emf);
        User user = dao.createUser("4hh", "1234");

//        System.out.println(user.getUsername());
        try {
            User verifiedUser = dao.verifyUser("4hh", "1234");
            System.out.println(verifiedUser.getName());

            Role verifiRole= dao.createRole("admin");


            User updatedUser = dao.addRoleToUser("Bibi", "instructor");
            System.out.println("Role added to user: " + updatedUser.getName());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Role createRole(String role) {
        EntityManager em = emf.createEntityManager();
        Role existingRole = em.find(Role.class, role);
        if (existingRole != null) {
            return existingRole;
        }
        // If the role doesn't exist, create and persist a new Role object
        Role newRole = new Role(role);

        try {
            em.getTransaction().begin();
            em.persist(newRole);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback in case of an exception
            // Handle or rethrow the exception as appropriate for your application
            throw new RuntimeException("Failed to create role due to: " + e.getMessage(), e);
        }

        return newRole;
    }


    @Override
    public User addRoleToUser(String username, String roleName) {

        EntityManager em = emf.createEntityManager();

        User user;
        try {
            em.getTransaction().begin();
           user= em.find(User.class, username);
           Role role= em.find(Role.class, roleName);

            user.addRole(role); // Modify the collection in the managed entity

            em.merge(user); // Ensure changes are cascaded to the database

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to add role to user due to: " + e.getMessage(), e);
        }

        return user;

    }
}
