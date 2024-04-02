package app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")

@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "EventId", nullable = false, unique = true)
    private int EventId;
    @Column(name= "Title")
    private String Title;
    @Column(name= "Description")
    private String Description;
    @Column(name= "Date")
    private LocalDate Date;
    @Column(name= "Time")
    private LocalDateTime Time;
    @Column(name= "Duration")
    private int Duration;
    @Column(name= "Capacity")
    private int Capacity;
    @Enumerated(EnumType.STRING)
    private Location location;
    @Column(name= "Instructor")
    private String Instructor;
    @Column(name= "Price")
    private double Price;
    @Column(name= "Status")
    private String Status;
    @OneToOne
    private Category category;
    @ManyToMany(mappedBy = "events")
    private  Set <User> users = new HashSet<>();

    public Event(String Title, String Description, LocalDate Date, LocalDateTime Time, int Duration, int Capacity, Location location, String Instructor, double Price, String Status, Category category) {
        this.Title = Title;
        this.Description = Description;
        this.Date = Date;
        this.Time = Time;
        this.Duration = Duration;
        this.Capacity = Capacity;
        this.location = location;
        this.Instructor = Instructor;
        this.Price = Price;
        this.Status = Status;
        this.category = category;
    }


    public void addUser(User user) {
        users.add(user);
        user.getEvents().add(this);
    }
    public void removeUser(User user) {
        users.remove(user);
        user.getEvents().remove(this);
    }
}
