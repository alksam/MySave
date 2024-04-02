package app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false, unique = true)
    private int id;
    private String name;
    private String password;
    private String email;
    private int phoneNumber;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_name", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
    private Set<Role> roles = new HashSet<>();
@ManyToMany
    @JoinTable(name = "user_events",
            joinColumns = @JoinColumn(name = "user_name", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "EventId"))
    private Set<Event> events = new HashSet<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;
        String salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, salt);
    }

    public boolean verifyUser(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
    public void addEvent(Event event) {
        events.add(event);
        event.getUsers().add(this);
    }
    public void removeEvent(Event event) {
        events.remove(event);
        event.getUsers().remove(this);
    }


    public Set<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        Set<String> rolesAsStrings = new HashSet<>();
        roles.forEach((role) -> {
            rolesAsStrings.add(role.getName());
        });
        return rolesAsStrings;
    }

}