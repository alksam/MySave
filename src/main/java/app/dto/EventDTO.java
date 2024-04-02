package app.dto;


import app.model.Category;
import app.model.Location;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
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

}
