package app.controllers;

import app.dao.CarDAOMock;
import app.model.Car;
import io.javalin.http.Handler;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CarEntityControler implements ICarEnityControler{


    CarDAOMock carDAOMock;

    public CarEntityControler(CarDAOMock carDAOMock) {
        this.carDAOMock = carDAOMock;
    }

    @Override
    public Handler getAll() {
        return ctx -> {
            try {
                ctx.json(carDAOMock.getAll());
            } catch (Exception e) {
                Logger.getGlobal().info(e.getMessage());
                ctx.status(500).json(new ErrorResponse(e.getMessage()));
            }
        };
    }

    @Override
    public Handler getById() {
        return ctx -> {
            try {
                Car car = carDAOMock.getById(Integer.parseInt(ctx.pathParam("id")));
                if (car != null) {
                    ctx.json(car);
                } else {
                    ctx.status(404).json(new ErrorResponse("Car not found"));
                }
            } catch (Exception e) {
                ctx.status(500).json(new ErrorResponse(e.getMessage()));
            }
        };
    }

    @Override
    public Handler create() {
        return ctx -> {
            try {
                Car car = ctx.bodyAsClass(Car.class);
                Car createdCar = carDAOMock.create(car);
                ctx.status(201).json(createdCar);
            } catch (Exception e) {
                ctx.status(500).json(new ErrorResponse(e.getMessage()));
            }
        };
    }
    @Override
    public Handler update() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Car car = ctx.bodyAsClass(Car.class);
                car.setId(id); // Sikrer at ID er sat korrekt
                Car updatedCar = carDAOMock.update(car);
                ctx.json(updatedCar);
            } catch (Exception e) {
                Logger.getGlobal().severe("Update error: " + e.getMessage());
                ctx.status(500).json(new ErrorResponse(e.getMessage()));
            }
        };
    }

    @Override
    public Handler delete() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Car deletedCar = carDAOMock.delete(id);
                if (deletedCar != null) {
                    ctx.json(deletedCar);
                } else {
                    ctx.status(404).json(new ErrorResponse("Car not found"));
                }
            } catch (Exception e) {
                ctx.status(500).json(new ErrorResponse(e.getMessage()));
            }
        };
    }

    private static class ErrorResponse {
        public final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}