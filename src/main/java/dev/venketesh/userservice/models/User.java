package dev.venketesh.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel{
    private String email;
    private String hashedPassword;
    private String name;
    private boolean isEmailVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
