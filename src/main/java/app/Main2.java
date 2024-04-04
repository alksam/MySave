package app;

import app.config.HibernateConfig;
import app.dao.EventDAO;
import app.dao.UserDAO;
import app.dao.UserDAO2;
import app.model.Event;
import app.model.Location;
import app.model.User;
import jakarta.persistence.EntityManagerFactory;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        UserDAO2 dao = new UserDAO2(emf);
        EventDAO eventDAO = new EventDAO(emf);
        UserDAO userDAO = new UserDAO(emf);
        User user2 = new User("scsac", "1234", "ggg@df", 5533112);

        User user3 = new User("fhhffsfff", "1234", "ggg@dff", 5533112);
        User user1 = dao.create(user2);
        User user = dao.create(user3);

        System.out.println(user.getName());
        Event event = new Event
                ("Yoga", "Yoga for beginners",
                        LocalDateTime.of(LocalDate.of(2021, 10, 10),
                                LocalTime.of(10, 0)), LocalTime.of(10, 0),
                        60, 20, Location.Aalborg, "Bibi", 100,
                        "Active", null);
        Event createdEvent = eventDAO.create(event);

        try {
            User verifiedUser = userDAO.verifyUser("fhhff", "1234");

            User updatedUser = userDAO.addRoleToUser("fhhff", "instructor");
            System.out.println("Role added to user: " + updatedUser.getName());
        } catch (Exception e) {
            e.printStackTrace();


        }
    }


}
