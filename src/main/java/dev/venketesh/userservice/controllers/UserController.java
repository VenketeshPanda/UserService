package dev.venketesh.userservice.controllers;

import dev.venketesh.userservice.dto.LoginRequestDTO;
import dev.venketesh.userservice.dto.LogoutRequestDTO;
import dev.venketesh.userservice.dto.SignUpRequestDTO;
import dev.venketesh.userservice.dto.UserDTO;
import dev.venketesh.userservice.exceptions.UserNotFoundException;
import dev.venketesh.userservice.models.Token;
import dev.venketesh.userservice.models.User;
import dev.venketesh.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    private UserDTO createUser(@RequestBody SignUpRequestDTO signUpRequestDTO){
        User user = userService.signup(signUpRequestDTO.getEmail(), signUpRequestDTO.getName(), signUpRequestDTO.getPassword());
        return UserDTO.from(user);
    }


    @PostMapping("/login")
    private Token loginUser(@RequestBody LoginRequestDTO loginRequestDTO) throws UserNotFoundException {
        return userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
    }

    @PostMapping("/logout")
    private ResponseEntity<Void> logoutUser(@RequestBody LogoutRequestDTO logoutRequestDTO){
        userService.logout(logoutRequestDTO.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    private UserDTO validateToken(@PathVariable("token") String token){
        User user = userService.validateToken(token);
        return UserDTO.from(user);
    }

}
