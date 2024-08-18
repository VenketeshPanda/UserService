package dev.venketesh.userservice.service;

import dev.venketesh.userservice.exceptions.UserNotFoundException;
import dev.venketesh.userservice.models.Token;
import dev.venketesh.userservice.models.User;
import dev.venketesh.userservice.repository.TokenRepository;
import dev.venketesh.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;


    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRepository userRepository,
                       TokenRepository tokenRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository=userRepository;
        this.tokenRepository=tokenRepository;
    }

    public User signup(String email, String name, String password){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(true);

        return userRepository.save(user);
    }


    public Token login(String email, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found with email" + email);
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            return null;
        }
        return generateToken(user);
    }

    private Token generateToken(User user){
        LocalDate localDate = LocalDate.now();
        LocalDate thirtyDaysLater =  localDate.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryAt(expiryDate);
        //128 bit alphanumeric string
        token.setValue(RandomStringUtils.random(128,true,true));
        token.setUser(user);
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    public String logout(String token){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(token,false);
        if(optionalToken.isEmpty()) return "Uh oh, please login";
        Token token1 = optionalToken.get();
        token1.setDeleted(true);
        tokenRepository.save(token1);
        return "Logout successful";
    }

    public User validateToken(String token){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token,false,new Date());
        if(optionalToken.isEmpty()) return null;
        return optionalToken.get().getUser();
    }
}
