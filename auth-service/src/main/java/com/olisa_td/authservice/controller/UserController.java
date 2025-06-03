package com.olisa_td.authservice.controller;


import com.olisa_td.authservice.dto.LoginRequest;
import com.olisa_td.authservice.dto.LoginResponse;
import com.olisa_td.authservice.dto.SignupRequest;
import com.olisa_td.authservice.dto.TokenResponse;
import com.olisa_td.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignupRequest signupRequest){
        this.userService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse(this.userService.login(loginRequest)));
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(this.userService.validateToken(authHeader));
    }

    @GetMapping("/validateOne")
    public ResponseEntity<Void> validateTokenOne(@RequestHeader("Authorization") String authHeader) {
        this.userService.validateToken(authHeader);
        return ResponseEntity.ok().build();
    }
}
