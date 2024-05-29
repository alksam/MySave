package app.dao;

import app.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.logging.Logger;

public class CarDAOMock implements IDAO<Car> {

    private static final Logger LOGGER = Logger.getLogger(CarDAOMock.class.getName());
    private static CarDAOMock instance;
    private static EntityManagerFactory emf;

    public CarDAOMock(EntityManagerFactory emf) {
        CarDAOMock.emf = emf;
    }

    public static CarDAOMock getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new CarDAOMock(emf);
        }
        return instance;
    }

    @Override
    public List<Car> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Car c", Car.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Car getById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Car.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Car create(Car entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return entity;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.severe("Error creating car: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Car update(Car entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Car updatedCar = em.merge(entity);
            em.getTransaction().commit();
            return updatedCar;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.severe("Error updating car: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }


    @Override
    public Car delete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Car car = em.find(Car.class, id);
            if (car != null) {
                em.getTransaction().begin();
                em.remove(car);
                em.getTransaction().commit();
            }
            return car;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.severe("Error deleting car: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}
