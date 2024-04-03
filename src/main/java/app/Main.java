package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.controllers.EventController;
import app.controllers.IEventController;
import app.controllers.ISecurityController;
import app.controllers.SecurityController;
import app.dao.EventDAO;
import app.handler.EventHandler;
import app.handler.IEventHandler;
import app.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Main {

    private static ISecurityController securityController = new SecurityController();
    private static ObjectMapper om = new ObjectMapper();
    private static EventDAO eventDAO= new EventDAO();
    private static IEventHandler eventHandler= new EventHandler();
    private static IEventController eventController= new EventController(eventDAO);
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        eventDAO = new EventDAO(emf);
        eventController = new EventController(eventDAO);
        startServer(7007);

    }

    public static void startServer(int port){

        ObjectMapper om = new ObjectMapper();
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig
                .initiateServer()
                .startServer(7007)
                .setExceptionHandling()
                .setRoute(getSecurityRoutes())
                .setupAccessManager()
                .setRoute(getSecuredRoutes())
                .setRoute(() -> {

                    getRoutes();

                })
                .checkSecurityRoles();
    }

    public static void getRoutes(){
        before(securityController.authenticate());
        path("/events", () -> {
            path("/", () -> {
                before(securityController.authenticate());
                get("/", eventController.getAllEvents(), Role.ANYONE);
                get("/{id}", eventController.getEventById(), Role.ANYONE);
                post("/add", eventController.createEvent(), Role.ANYONE);
                put("/update/{id}", eventController.updateEvent(),Role.ANYONE);
                delete("/delete/{id}", eventController.deleteEvent(),Role.ANYONE);
                get("/error", ctx -> {
                    throw new Exception(String.valueOf(ApplicationConfig.getInstance().setExceptionHandling()));
                });
            });
        });
    }

    public static void closeServer () {
        ApplicationConfig.getInstance().stopServer();
    }


    public static EndpointGroup getSecurityRoutes() {
        return ()->{
            path("/auth", ()->{
                post("/login", securityController.login(),Role.ANYONE);
                post("/register", securityController.register(),Role.ANYONE);
            });
        };
    }

    public static EndpointGroup getSecuredRoutes(){
        return ()->{
            path("/protected", ()->{
                before(securityController.authenticate());
                get("/user",(ctx)->ctx.json(om.createObjectNode().put("msg",  "Hello from USER Protected")),Role.USER);
                get("/instructor",(ctx)->ctx.json(om.createObjectNode().put("msg",  "Hello from INSTRUCTOR Protected")),Role.INSTRUCTOR);
                get("/admin",(ctx)->ctx.json(om.createObjectNode().put("msg",  "Hello from ADMIN Protected")),Role.ADMIN);
            });
        };
    }

    public enum Role implements RouteRole {
        ANYONE,
        USER,
        ADMIN,
        INSTRUCTOR

    }
}