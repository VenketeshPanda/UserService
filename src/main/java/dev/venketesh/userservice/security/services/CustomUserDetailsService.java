package dev.venketesh.userservice.security.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.venketesh.userservice.models.User;
import dev.venketesh.userservice.repository.UserRepository;
import dev.venketesh.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@JsonDeserialize
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found with email" + username);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(userOptional.get());
        return customUserDetails;
    }
}
