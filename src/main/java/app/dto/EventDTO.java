package app.dto;


import app.model.Category;
import app.model.Event;
import app.model.Location;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@NoArgsConstructor
@Setter
public class EventDTO {

    private String Title;
    private String Description;
    private LocalDate Date;
    private LocalDateTime Time;
    private int Duration;
    private int Capacity;
    private Location location;
    private String Instructor;
    private double Price;
    private Category category;
    private String Image;
    private String Status;

    public EventDTO(String title, String description, LocalDate date, LocalDateTime time, int duration, int capacity, Location location, String instructor, double price, Category category, String image, String status) {
        Title = title;
        Description = description;
        Date = date;
        Time = time;
        Duration = duration;
        Capacity = capacity;
        this.location = location;
        Instructor = instructor;
        Price = price;
        this.category = category;
        Image = image;
        Status = status;
    }

    public EventDTO(Event event) {
        Title = event.getTitle();
        Description = event.getDescription();
        Date = LocalDate.from(event.getDate());
        Time = LocalDateTime.from(event.getTime());
        Duration = event.getDuration();
        Capacity = event.getCapacity();
        this.location = event.getLocation();
        Instructor = event.getInstructor();
        Price = event.getPrice();
        this.category = event.getCategory();
        Image = Arrays.toString(event.getImage());
        Status = event.getStatus();
    }
}
