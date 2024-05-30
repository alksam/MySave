package app.controllers;

import app.dao.CarDAOMock;
import app.dto.CarDTO;
import app.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CarEntityControler implements ICarEnityControler {
    private final CarDAOMock carDAOMock;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CarEntityControler(CarDAOMock carDAOMock) {
        this.carDAOMock = carDAOMock;
    }

    private Car convertToEntity(CarDTO carDTO) {
        return new Car(carDTO);
    }

    private List<CarDTO> convertToCarDTO(List<Car> cars) {
        return cars.stream()
                .map(CarDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Handler getAll() {
        return ctx -> {
            Logger.getGlobal().info("Fetching all cars");
            try {
                List<Car> cars = carDAOMock.getAll();
                List<CarDTO> carDTOS = convertToCarDTO(cars);
                ctx.json(carDTOS);
            } catch (Exception e) {
                Logger.getGlobal().severe(e.getMessage());
                ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
            }
        };
    }

    @Override
    public Handler getById() {
        return ctx -> {
            Logger.getGlobal().info("Fetching car by ID: " + ctx.pathParam("id"));
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Car car = carDAOMock.getById(id);
                if (car != null) {
                    ctx.json(new CarDTO(car));
                } else {
                    ctx.status(404).json(new ErrorResponse("Car not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorResponse("Invalid car ID format"));
            } catch (Exception e) {
                Logger.getGlobal().severe(e.getMessage());
                ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
            }
        };
    }

    @Override
    public Handler create() {
        return ctx -> {
            Logger.getGlobal().info("Creating car");
            try {
                CarDTO carDTO = ctx.bodyAsClass(CarDTO.class);
                Car car = convertToEntity(carDTO);
                Car createdCar = carDAOMock.create(car);
                ctx.status(201).json(new CarDTO(createdCar));
            } catch (Exception e) {
                Logger.getGlobal().severe(e.getMessage());
                ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
            }
        };
    }

    @Override
    public Handler update() {
        return ctx -> {
            Logger.getGlobal().info("Updating car with ID: " + ctx.pathParam("id"));
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                CarDTO carDTO = ctx.bodyAsClass(CarDTO.class);
                Car carToUpdate = carDAOMock.getById(id);
                if (carToUpdate == null) {
                    ctx.status(404).json(new ErrorResponse("Car not found"));
                    return;
                }
                carToUpdate.updateFromDTO(carDTO);
                Car updatedCar = carDAOMock.update(carToUpdate);
                ctx.json(new CarDTO(updatedCar));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorResponse("Invalid car ID format"));
            } catch (Exception e) {
                Logger.getGlobal().severe(e.getMessage());
                ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
            }
        };
    }

    @Override
    public Handler delete() {
        return ctx -> {
            Logger.getGlobal().info("Deleting car with ID: " + ctx.pathParam("id"));
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Car car = carDAOMock.getById(id);
                if (car == null) {
                    ctx.status(404).json(new ErrorResponse("Car not found"));
                    return;
                }
                carDAOMock.delete(id);
                ctx.status(204).json(new SuccessResponse("Car deleted successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(new ErrorResponse("Invalid car ID format"));
            } catch (Exception e) {
                Logger.getGlobal().severe(e.getMessage());
                ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
            }
        };
    }

    private static class ErrorResponse {
        public final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    private static class SuccessResponse {
        public final String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}
