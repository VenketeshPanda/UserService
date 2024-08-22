package dev.venketesh.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.venketesh.userservice.models.Role;
import dev.venketesh.userservice.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@JsonDeserialize
@Getter
@Setter
//This custom user detail class will act as a User class for the Spring Security(Kind of DTO but not DTO)
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<CustomGrantedAuthority> authorities;
    private long userId;


    public CustomUserDetails(User user){
        this.username=user.getEmail();
        this.password=user.getHashedPassword();
        this.accountNonExpired=true;
        this.accountNonLocked =true;
        this.credentialsNonExpired =true;
        this.enabled=true;
        //int grantedAuthorities we will add the roles :)
        this.authorities=new ArrayList<>();
        for(Role role: user.getRoles()){
            authorities.add(new CustomGrantedAuthority(role));
        }
        this.userId=user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
