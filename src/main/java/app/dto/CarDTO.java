package app.dto;

import app.model.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CarDTO {

    private int id;
    private String brand;
    private String model;
    private String make;
    private int year;
    private int firstRegistrationDate;
    private double price;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.make = car.getMake();
        this.year = car.getYear();
        this.firstRegistrationDate = car.getFirstRegistrationDate();
        this.price = car.getPrice();
    }
}
