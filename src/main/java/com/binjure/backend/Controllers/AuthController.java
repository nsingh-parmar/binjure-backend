package com.binjure.backend.Controllers;


import com.binjure.backend.CustomizedResponse;
import com.binjure.backend.Models.UserModel;
import com.binjure.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@CrossOrigin(origins="https://binjure.herokuapp.com")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(value="/authenticate", consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity login(@RequestBody UserModel user) {

       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
           String userID = userService.getUserByEmail(user.getEmail()).getId();
           var response = new CustomizedResponse(
                   "Login successful!",
                   Collections.singletonList(userID));
           return new ResponseEntity(response, HttpStatus.OK);
       }
       catch(BadCredentialsException ex) {
           var response = new CustomizedResponse("Please enter correct credentials!", null);
           return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
       }
    }
}