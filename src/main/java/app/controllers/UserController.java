package app.controllers;

import app.config.HibernateConfig;
import app.dao.UserDAO;
import app.dto.UserDTO;
import app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Handler;

public class UserController implements IUserController{

    UserDAO userDAO = new UserDAO(HibernateConfig.getEntityManagerFactory());
    ObjectMapper objectMapper = new ObjectMapper();
    //UserDTO userDTO= new UserDTO();
   // User user= new User();


    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Handler getAllUsers() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                ctx.json(userDAO.getAlleUser());
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
    }

    @Override
    public Handler createUser() {
        return (ctx) -> {
            UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);

            try {
                User newUser = userDAO.createUser(userDTO.getName(), userDTO.getPassword(),userDTO.getEmail(), userDTO.getPhoneNumber());
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
                ctx.json(userDAO.getUserById(id));
            } catch (Exception e) {
                ctx.status(500);
                ctx.json(returnObject.put("msg", "Internal server error"));
            }
        };
    }

    @Override
    public Handler updateUser() {
        return (ctx) -> {

            UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);

            int userId = Integer.parseInt(ctx.pathParam("id"));

            try {
                User userToUpdate = userDAO.getUserById(userId);


                userToUpdate.setName(userDTO.getName());
                userToUpdate.setEmail(userDTO.getEmail());
                userToUpdate.setPassword(userDTO.getPassword());

                User updatedUser = userDAO.update(userToUpdate);
                ctx.json(updatedUser);
            }catch (Exception e) {

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

}
