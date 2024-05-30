package app.model;

import app.config.HibernateConfig;
import app.dao.CarDAOMock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate {

    public static void main(String[] args) {


        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        CarDAOMock dao = new CarDAOMock(emf);




        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();







        Car car1 = new Car("Toyota", "Corolla", "d", 20000, 2020, 2220);
        Car car2 = new Car("mazada", "2", "d", 2015, 20217, 2220);
        Car car3 = new Car("bmw", "21", "d", 2018, 201, 10000);
        dao.create(new Car("Toyota", "Corolla", "d", 20000, 2011, 2220));
        dao.create(new Car("mazada", "2", "d", 2015, 2031, 2220));
        dao.create(new Car("bmw", "21", "d", 2018, 2001, 10000));


        em.persist(car1);
        em.persist(car2);
        em.persist(car3);




        dao.getAll();
        System.out.println(dao.getAll());

        dao.getById(1);
        System.out.println(dao.getById(1));

        dao.update(new Car("Toyota", "Corolla", "d", 20000, 2021, 2220));

//        dao.delete(2);

        em.getTransaction().commit();
        em.close();
System.out.println("_____________________________");

        dao.getAll();
        System.out.println(dao.getAll());

        dao.getById(1);
        System.out.println(dao.getById(1));

        dao.update(new Car("Toyota", "Corolla", "d", 20000, 2021, 2220));





    }
}

