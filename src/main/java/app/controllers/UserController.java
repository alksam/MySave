package app.controllers;

import app.config.HibernateConfig;
import app.dao.UserDAO;
import app.dao.UserDAO2;
import app.dto.EventDTO;
import app.dto.UserDTO;
import app.functionallity.UserFunctionality;
import app.model.Event;
import app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class UserController implements IUserController{

    UserDAO2 userDAO = new UserDAO2();
    ObjectMapper objectMapper = new ObjectMapper();

UserFunctionality userFunctionality = new UserFunctionality();


    public UserController(UserDAO2 userDAO) {
        this.userDAO = userDAO;
    }

    private User convertToEntity(UserDTO userDTO) {

        return new User(userDTO);
    }

    @Override
    public Handler getAllUsers() {
        return (ctx) -> {
            try {
                List<User> users = userDAO.getAlleUser();
                List<UserDTO> userDTOs = new ArrayList<>();
                for (User user : users) {
                    userDTOs.add(userFunctionality.convertToDTO(user));
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
                UserDTO userDTO = userFunctionality.convertToDTO(userbyid);
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
    public Handler addUserToEvent() {
        return null;
    }

    @Override
    public Handler removeUserFromEvent() {
        return null;
    }

    @Override
    public Handler getUsersForEvent() {
        return null;
    }

    @Override
    public Handler getAllRegistereUsers() {
        return null;
    }

}
