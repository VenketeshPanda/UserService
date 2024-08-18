package dev.venketesh.userservice.dto;

import dev.venketesh.userservice.models.Role;
import dev.venketesh.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String name;
    private List<Role> roles;

    public static UserDTO from(User user){
        if(user==null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
