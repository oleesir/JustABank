package com.olisa_td.authservice.controller;


import com.olisa_td.authservice.domain.PageResponse;
import com.olisa_td.authservice.dto.LoginRequest;
import com.olisa_td.authservice.dto.LoginResponse;
import com.olisa_td.authservice.dto.SignupRequest;
import com.olisa_td.authservice.dto.TokenResponse;
import com.olisa_td.authservice.jpa.User;
import com.olisa_td.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid  @RequestBody SignupRequest signupRequest){
        this.userService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse(this.userService.login(loginRequest)));
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(this.userService.validateToken(authHeader));
    }

    @GetMapping("/users/{pageNum}/{pageSize}")
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    public ResponseEntity<PageResponse<User>> getAllUsers (@PathVariable int pageNum, @PathVariable int pageSize) {
        return ResponseEntity.ok(this.userService.getAllUsers(pageNum,pageSize));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(this.userService.getUser(id));
    }

}
