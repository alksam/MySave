package app.controllers;

import app.dao.EventDAO;


import io.javalin.http.Handler;

public interface IEventController {
    Handler getAllEvents();
    Handler getEventById();
    Handler createEvent();
    Handler updateEvent();
    Handler deleteEvent();


}
