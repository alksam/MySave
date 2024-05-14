package app.dto;



import app.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Setter
public class UserDTO {

    private String name;
    private String password;
    private String email;
    private int phoneNumber;

    private Set<String> roles = new HashSet<>();
    private Set<String> events= new HashSet<>();

    public UserDTO(String name, String password, Set<String> roles , Set<String> events) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.events = events;
    }

    public UserDTO(String name, String password, String email, int phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserDTO(User user) {
        this.name = name;
        this.password = password;
    }

//    public UserDTO(User user){
//        this.name = user.getName();
//        this.password = user.getPassword();
//       this.roles = user.getRolesAsStrings();
//    }
    public UserDTO(String name, Set<String> roleSet){
        this.name = name;
        this.roles = roleSet;
    }


    public static List<UserDTO> toUserDTOList(List<User> users) {
        List<UserDTO> userDTOList =  new ArrayList<>();
        for (User user : users) {
            userDTOList.add(new UserDTO(user.getName(), user.getRolesAsStrings()));
        }
        return userDTOList;

    }

//    public String getName() {
//        return name;
//    }
//
//    public Set<String> getRoles() {
//        return roles;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public int getPhoneNumber() {
//        return phoneNumber;
//    }
}
