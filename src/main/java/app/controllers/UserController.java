package app.controllers;

import app.config.HibernateConfig;
import app.dao.EventDAO;
import app.dao.UserDAO;
import app.dao.UserDAO2;
import app.dto.EventDTO;
import app.dto.UserDTO;
import app.model.Event;
import app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserController implements IUserController{

    UserDAO2 userDAO = new UserDAO2();
    ObjectMapper objectMapper = new ObjectMapper();

    EventDAO eventDAO = new EventDAO();


    public UserController(UserDAO2 userDAO) {
        this.userDAO = userDAO;
    }

    private User convertToEntity(UserDTO userDTO) {

        return new User(userDTO);
    }


    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }

    @Override
    public Handler getAllUsers() {
        return (ctx) -> {
            try {
                List<User> users = userDAO.getAlleUser();
                List<UserDTO> userDTOs = new ArrayList<>();
                for (User user : users) {
                    userDTOs.add(convertToDTO(user));
                }
                ctx.json(userDTOs);
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };
    }

    @Override
    public Handler createUser() {
        return (ctx) -> {
            UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);

            try {
                User userToCreate = convertToEntity(userDTO);

                User newUser = userDAO.create(userToCreate);
                ctx.status(201);
                ctx.json(newUser);
            } catch (Exception e) {

                ctx.status(500); // Internal Server Error
                ctx.json("Internal server error");
            }
        };




    }

    @Override
    public Handler getUserById() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                User  userbyid = userDAO.getUserById(id);
                UserDTO userDTO = convertToUserDTO2(userbyid);
                ctx.json(userDTO);

            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
    }


    @Override
    public Handler updateUser() {
        return (ctx) -> {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
            try {
                User userToUpdate = userDAO.getUserById(userId);
                if (userToUpdate != null) {
                    userToUpdate.setName(userDTO.getName());
                    userToUpdate.setEmail(userDTO.getEmail());
                    userToUpdate.setPassword(userDTO.getPassword());
                    User updatedUser = userDAO.update(userToUpdate);
                    ctx.json(updatedUser);
                } else {
                    ctx.status(404);
                    ctx.json("User not found");
                }
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };
    }



    @Override
    public Handler deleteUser() {
        return (ctx) -> {
            int userId = Integer.parseInt(ctx.pathParam("id"));

            try {
                userDAO.deleteUser(userId);

                ctx.json("User deleted successfully");
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };
    }

    @Override
    public Handler registerUserForEvent() {
        ObjectNode returnObject = objectMapper.createObjectNode();
        return (ctx) -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            int eventId = Integer.parseInt(ctx.pathParam("eventId"));

            try {
                User user = userDAO.getUserById(userId);
                Event event = eventDAO.getEventById(eventId);
                eventDAO.addUserToEvent(user, event);
                UserDTO userDTO = convertToUserDTO2(user);
                ctx.json(userDTO);

                ctx.json("User added to event successfully");
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };

    }

    @Override
    public Handler removeUserFromEvent() {
        ObjectNode returnObject = objectMapper.createObjectNode();
        return (ctx) -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            int eventId = Integer.parseInt(ctx.pathParam("eventId"));

            try {


                ctx.json("User removed from event successfully");
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };
    }

    @Override
    public Handler getUsersForEvent() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            int eventId = Integer.parseInt(ctx.pathParam("eventId"));

            try {
                Event event = eventDAO.getEventById(eventId);
              List<User> users = eventDAO.getUsersForEvent(event);
                List<UserDTO> userDTOs = convertToUserDTO(users);
                ctx.json(userDTOs);
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };
    }

    @Override
    public Handler getAllRegistereUsers() {
        ObjectNode returnObject = objectMapper.createObjectNode();
        return (ctx) -> {

            try {
//                Event event = eventDAO.getEventById(Integer.parseInt(ctx.pathParam("eventId")));

                List<User> users = eventDAO.getAllRegistereUsers();
                List<UserDTO> userDTOs = convertToUserDTO(users);
                ctx.json(userDTOs);
            } catch (Exception e) {
                ctx.status(500);
                ctx.json("Internal server error");
            }
        };
    }

    private List<UserDTO> convertToUserDTO(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(convertToUserDTO2(user));
        }
        return userDTOs;
    }


    private UserDTO convertToUserDTO2(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }


}
