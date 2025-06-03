package com.olisa_td.authservice.service;

import com.olisa_td.authservice.dto.LoginRequest;
import com.olisa_td.authservice.dto.SignupRequest;
import com.olisa_td.authservice.dto.TokenResponse;
import com.olisa_td.authservice.exception.domain.EmailExistException;
import com.olisa_td.authservice.exception.domain.InValidTokenException;
import com.olisa_td.authservice.exception.domain.UserNotFoundException;
import com.olisa_td.authservice.jpa.Role;
import com.olisa_td.authservice.jpa.User;
import com.olisa_td.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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
        user.setRole(Role.USER);

        this.userRepository.save(user);

    }



    public String login(LoginRequest loginRequest) {

        User user = this.userRepository.findByEmail(loginRequest.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequest.getPassword(),
                        u.getPassword()))
                .orElseThrow(() -> new UserNotFoundException("Email or password is incorrect."));

        return jwtService.generateToken(user.getId().toString(),user.getRole().name());
    }



    public TokenResponse validateToken(String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InValidTokenException("Token not found.");
        }

        String token = authHeader.substring(7);

         return jwtService.getEmailAndRoleFromToken(token);

    }

    public void validateTokenOne(String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InValidTokenException("Token not found.");
        }

        String token = authHeader.substring(7);

         jwtService.validateToken(token);

    }


}


