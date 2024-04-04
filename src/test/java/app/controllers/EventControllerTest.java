package app.controllers;


import app.dao.EventDAO;
import app.model.Event;
import app.model.Location;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import app.config.HibernateConfig;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.antlr.tool.ErrorManager.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class EventControllerTest {
    private static EntityManagerFactory emf;
    private static EventDAO eventDAO;

    private static IEventController eventController;
    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        eventDAO = new EventDAO(emf);
        eventController= new EventController(eventDAO);

 }


    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllEvents() {

        List<Event> events = (List<Event>) eventController.getAllEvents();
        Assertions.assertNotNull(String.valueOf(events));
        assertTrue(!events.isEmpty(), "The list of events should not be empty");

    }

    @Test
    void getEventById() {

        List<Event> event = (List<Event>) eventDAO.getEventById(1);
        Assertions.assertNotNull(event);
    }

    @Test
    void createEvent() throws IOException {
        // Create a new Event object
        Event newEvent = new Event();
        // Set properties for the new Event
        newEvent.setTitle("Yoga Class");
        newEvent.setDescription("An introductory yoga class for beginners.");
        newEvent.setDate(LocalDateTime.of(2024, 3, 20, 18, 0)); // Example date and time
        newEvent.setTime(LocalTime.of(1, 30)); // Duration of 1 hour and 30 minutes
        newEvent.setDuration(90); // Duration in minutes
        newEvent.setCapacity(20);
        newEvent.setLocation(Location.Aalborg); // Assuming Location is an enum
        newEvent.setInstructor("John Doe");
        newEvent.setPrice(100.00);
        newEvent.setStatus("Scheduled");

        Event createdEvent = eventDAO.create(newEvent);
        assertNotNull(createdEvent);
        assertNotNull(createdEvent.getEventId());
        assertEquals("Yoga Class", createdEvent.getTitle());

    }


    @Test
    void updateEvent() {
    }

    @Test
    void deleteEvent() {

        eventDAO.delete(1);


    }
}