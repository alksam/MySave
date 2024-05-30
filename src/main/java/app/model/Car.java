package app.model;

import app.dto.CarDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "car")
@Entity
@NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carid", nullable = false, unique = true)
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "year")
    private int year;

    @Column(name = "first_registration_date")
    private int firstRegistrationDate;

    @Column(name = "price")
    private double price;



    public Car( String brand, String model, String make, int year, int firstRegistrationDate, double price) {

        this.brand = brand;
        this.model = model;
        this.make = make;
        this.year = year;
        this.firstRegistrationDate = firstRegistrationDate;
        this.price = price;

    }

    public Car(CarDTO carDTO) {
        this.brand = carDTO.getBrand();
        this.model = carDTO.getModel();
        this.make = carDTO.getMake();
        this.year = carDTO.getYear();
        this.firstRegistrationDate = carDTO.getFirstRegistrationDate();
        this.price = carDTO.getPrice();
    }

    public void updateFromDTO(CarDTO carDTO) {
        this.brand = carDTO.getBrand();
        this.model = carDTO.getModel();
        this.make = carDTO.getMake();
        this.year = carDTO.getYear();
        this.firstRegistrationDate = carDTO.getFirstRegistrationDate();
        this.price = carDTO.getPrice();
    }




}
