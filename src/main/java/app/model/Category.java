package app.model;

import app.model.Event;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "categories")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false, unique = true)
    private int categoryID;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany
    private Set<Event> eventSet;
}
