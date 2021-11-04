package org.example.controllers;

import org.example.DTOs.AuthenticationRequestDto;
import org.example.entities.UserEntity;
import org.example.security.jwt.JwtTokenProvider;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {


            String username = requestDto.getUserName();
            UserEntity user = userService.findByUsername(username);
            if (user == null) {

                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));


            String token = jwtTokenProvider.createToken(username, user.getRoles());


            return ResponseEntity.ok().body("You have successfully logged in with "+ username+"! Here is your token: "+token);

    }

}

