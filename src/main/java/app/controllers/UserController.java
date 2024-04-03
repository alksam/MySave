package app.controllers;

import app.config.HibernateConfig;
import app.dao.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Handler;

public class UserController implements IUserController{

    UserDAO userDAO = new UserDAO(HibernateConfig.getEntityManagerFactory());
    ObjectMapper objectMapper = new ObjectMapper();
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
        return null;
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
        return null;
    }

    @Override
    public Handler deleteUser() {
        return null;
    }
}
