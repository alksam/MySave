package app.controllers;

import app.dao.EventDAO;
import app.dto.EventDTO;
import app.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.HttpStatus;
import io.javalin.http.Handler;



import javax.naming.Context;


public class EventController implements IEventController{
    EventDAO eventDAO = new EventDAO();
    ObjectMapper objectMapper = new ObjectMapper();
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
                EventDTO eventInput = ctx.bodyAsClass(EventDTO.class);
                Event created = eventDAO.create(eventInput.getCategory().getEvent());
                ctx.status(HttpStatus.CREATED).json(new EventDTO(created));
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
    }

    @Override
    public Handler updateEvent() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                EventDTO eventInput = ctx.bodyAsClass(EventDTO.class);
                int id = Integer.parseInt(ctx.pathParam("id"));
                Event updated = eventDAO.getEventById(id);
                eventInput.getEventId();
                ctx.json(eventDAO.update(updated).getEventId());

            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
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
