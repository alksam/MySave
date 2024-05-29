package app.controllers;

import app.dao.CategoryDAO;
import app.dao.EventDAO;
import app.dto.EventDTO;
import app.functionallity.EventFunctionality;
import app.model.Event;
import app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.HttpStatus;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class EventController implements IEventController{
    EventDAO eventDAO = new EventDAO();
    ObjectMapper objectMapper = new ObjectMapper();

    EventFunctionality eventFunctionality= new EventFunctionality();

    public EventController(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public Handler getAllEvents() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                ctx.json(eventDAO.getAlleEvents());
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
        }





    @Override
    public Handler getEventById() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                ctx.json(eventDAO.getEventById(id));
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
    }

    @Override
    public Handler createEvent() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                // Parse the incoming JSON to an EventDTO
                EventDTO eventInput = ctx.bodyAsClass(EventDTO.class);

                // Convert EventDTO to Event entity
                Event eventToCreate = convertToEntity(eventInput);

                // Create the event in the database
                Event createdEvent = eventDAO.create(eventToCreate);

                // Convert the created Event back to EventDTO for response
                EventDTO createdEventDTO = new EventDTO(createdEvent);


                // Set status as CREATED and return the created event
                ctx.status(HttpStatus.CREATED).json(createdEventDTO);
            } catch (Exception e) {

                e.printStackTrace();
                ctx.status(500).json(returnObject.put("msg", "Internal server error: " + e.getMessage()));
            }
        };
    }

    private Event convertToEntity(EventDTO eventDTO) {

        return new Event(eventDTO);
    }


    @Override
    public Handler updateEvent() {
        return (ctx) -> {
            try {
                EventDTO eventInput = ctx.bodyAsClass(EventDTO.class);
                Event eventToUpdate = convertToEntity(eventInput);
                Event updatedEvent = eventDAO.update(eventToUpdate);
                ctx.json(updatedEvent);
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(e.getMessage());
            }
        };


    }

    @Override
    public Handler deleteEvent() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                eventDAO.delete(id);
                ctx.status(204);
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
    }


}
