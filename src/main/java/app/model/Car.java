package app.model;

import app.dto.CarDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "make")
    private String make;
    @Column(name = "year")
    private int year;
    @Column(name = "first_registration_date" ,nullable = false)
    private int firstRegistrationDate;
    @Column(name = "price")
    private double price;

    public Car(String brand, String model, String make, int year, int firstRegistrationDate, double price) {
        this.brand = brand;
        this.model = model;
        this.make = make;
        this.year = year;
        this.firstRegistrationDate = firstRegistrationDate;
        this.price = price;
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
