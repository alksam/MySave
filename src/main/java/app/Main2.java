package app;

import app.config.HibernateConfig;
import app.dao.EventDAO;
import app.dao.UserDAO;
import app.model.Event;
import app.model.Location;
import app.model.User;
import jakarta.persistence.EntityManagerFactory;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        UserDAO dao = new UserDAO(emf);
        EventDAO eventDAO = new EventDAO(emf);

User user1 = dao.createUser("deee", "1244");
        User user = dao.createUser("mmee", "1234");

        System.out.println(user.getName());
        Event event = new Event
                ("Yoga", "Yoga for beginners",
                        LocalDateTime.of(LocalDate.of(2021, 10, 10),
                                LocalTime.of(10, 0)), LocalTime.of(10, 0),
                        60, 20, Location.Aalborg, "Yoga instructor", 100,
                        "Active", null);


        List<Event> events =new ArrayList(Arrays.asList(new Event("Yoga", "Yoga for beginners",
                LocalDateTime.of(LocalDate.of(2021, 10, 10),
                        LocalTime.of(10, 0)), LocalTime.of(10, 0),
                60, 20, Location.Aalborg, "Yoga instructor", 100,
                "Active", null),
                new Event("Yoga", "Yoga for beginners",
                        LocalDateTime.of(LocalDate.of(2021, 10, 10),
                                LocalTime.of(10, 0)), LocalTime.of(10, 0),
                        60, 20, Location.Aalborg, "Yoga instructor", 100,
                        "Active", null)));


        Event createdEvent = eventDAO.create(event);
        eventDAO.addUserToEvent(user, createdEvent);
        eventDAO.getAlleEvents();

        try {
            User verifiedUser = dao.verifyUser("mmee", "1234");
            System.out.println(verifiedUser.getName());
        } catch (Exception e) {
            e.printStackTrace();


        }
    }



}
