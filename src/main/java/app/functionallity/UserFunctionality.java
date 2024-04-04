package app.functionallity;


import app.dto.UserDTO;

import app.model.User;

public class UserFunctionality {
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }


}
