package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.controllers.*;
import app.dao.EventDAO;
;
import app.dao.UserDAO;
import app.dao.UserDAO2;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Route {

    private static ISecurityController securityController = new SecurityController();
    private static ObjectMapper om = new ObjectMapper();
    private static EventDAO eventDAO= new EventDAO();
    private static IEventController eventController= new EventController(eventDAO);
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    private static UserDAO2 dao= new UserDAO2(emf);
    private static UserDAO userDAO = new UserDAO(emf);
    private static IUserController userController= new UserController(dao);

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        eventDAO = new EventDAO(emf);
        eventController = new EventController(eventDAO);
        startServer(7007);
        userController= new UserController(dao);

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
                .setRoute(getRoutes())
                .setRoute(getUserRoutes())
                .setRoute(getRegisterRoutes())
                .checkSecurityRoles();
    }

    public static EndpointGroup getRoutes() {
        return () -> {
            before(securityController.authenticate());
            path("/events", () -> {
                get("/", eventController.getAllEvents(), Role.ANYONE);
                get("/{id}", eventController.getEventById(), Role.ANYONE);
                post("/create", eventController.createEvent(), Role.ANYONE);
                put("/update/{id}", eventController.updateEvent(), Role.ANYONE);
                delete("/delete/{id}", eventController.deleteEvent(), Role.ANYONE);
                get("/error", ctx -> {
                    throw new Exception(String.valueOf(ApplicationConfig.getInstance().setExceptionHandling()));
                });

            });
        };
    }


    public  static EndpointGroup getRegisterRoutes(){
        return ()->{
            path("/register", ()->{
              get("/all/{eventId}", userController.getAllRegistereUsers(), Role.ANYONE);
                get("//{userId}/{eventId}", userController.getUsersForEvent(), Role.ANYONE);
                post("/create/{userId}/{eventId}", userController.registerUserForEvent(), Role.ANYONE);
                delete("/delete/{userId}/{eventId}", userController.removeUserFromEvent(), Role.ANYONE);
                get("/error", ctx -> {
                    throw new Exception(String.valueOf(ApplicationConfig.getInstance().setExceptionHandling()));
                });
            });
        };
    }

    public static EndpointGroup getUserRoutes(){
       return()-> {

           path("/users", () -> {
               get("/all", userController.getAllUsers(), Role.ANYONE);
               get("/{id}", userController.getUserById(), Role.ANYONE);
               post("/create", userController.createUser(), Role.ANYONE);
               put("/update/{id}", userController.updateUser(), Role.ANYONE);
               delete("/delete/{id}", userController.deleteUser(), Role.ANYONE);
               get("/error", ctx -> {
                   throw new Exception(String.valueOf(ApplicationConfig.getInstance().setExceptionHandling()));
               });
           });
       };
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