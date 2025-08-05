package com.olisa_td.authservice.service;

import com.olisa_td.authservice.domain.PageResponse;
import com.olisa_td.authservice.dto.LoginRequest;
import com.olisa_td.authservice.dto.SignupRequest;
import com.olisa_td.authservice.dto.TokenResponse;
import com.olisa_td.authservice.exception.domain.EmailExistException;
import com.olisa_td.authservice.exception.domain.InValidTokenException;
import com.olisa_td.authservice.exception.domain.UserNotFoundException;
import com.olisa_td.authservice.jpa.Role;
import com.olisa_td.authservice.jpa.User;
import com.olisa_td.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;



    public void registerUser(SignupRequest signupRequest) {
        this.userRepository.findByEmail(signupRequest.getEmail())
                .ifPresent(user -> {
                    throw new EmailExistException("Email already exists");
                });


        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setAddress(signupRequest.getAddress());
        user.setPassword(this.passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(Role.ROLE_USER);

        this.userRepository.save(user);

    }



    public String login(LoginRequest loginRequest) {

        User user = this.userRepository.findByEmail(loginRequest.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequest.getPassword(),
                        u.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("Email or password is incorrect."));

        return this.jwtService.generateToken(user.getId().toString(),user.getRole().name());
    }



    public User getUser(String id) {

        return this.userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }



    public TokenResponse validateToken(String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InValidTokenException("Invalid token.");
        }

        String token = authHeader.substring(7);

         return this.jwtService.getEmailAndRoleFromToken(token);

    }


}


