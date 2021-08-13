package com.binjure.backend.Controllers;

import com.binjure.backend.CustomizedResponse;
import com.binjure.backend.Models.UserModel;
import com.binjure.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins="https://binjure.herokuapp.com")
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

   @GetMapping("/api/users")
    public ResponseEntity getUsers() {
        var response = new CustomizedResponse("All users", userService.getUsers());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity getUserById(@PathVariable("id") String id) {
        var fetchResponse = userService.getUser(id);
        try {
            String fetchId = fetchResponse.getId();
            var response = new CustomizedResponse(
                    "Returning user with id: " + fetchId,
                    Collections.singletonList(fetchResponse));

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (NullPointerException nex) {
            return new ResponseEntity(
                    new CustomizedResponse("No user found with this ID", null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/api/users/by/{email}")
    public ResponseEntity getUserByEmail(@PathVariable("email") String email) {
        var response = new CustomizedResponse(
             "Returning user with email: " + email,
             Collections.singletonList(userService.loadUserByUsername(email)));

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping(value="/api/users", consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity createUser(@RequestBody UserModel user) {
        var email = user.getEmail();
        if(user.getFirstName().isEmpty() || user.getLastName().isEmpty() || email.isEmpty() || user.getPassword().isEmpty()) {
            var response = new CustomizedResponse("Cannot create user with incomplete details!", null);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                String userID = userService.getUserByEmail(user.getEmail()).getId();
                if (userID.isEmpty()) {
                    var response = new CustomizedResponse(
                            "User created successfully!",
                            Collections.singletonList(userService.createUser(user)));
                    return new ResponseEntity(response, HttpStatus.CREATED);
                } else {
                    var response = new CustomizedResponse("User of this email already exists!", null);
                    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
                }
            } catch (NullPointerException nex) {
                var response = new CustomizedResponse(
                        "User created successfully!",
                        Collections.singletonList(userService.createUser(user)));
                return new ResponseEntity(response, HttpStatus.CREATED);
            }
        }
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id")String id) {
        try {
            boolean deletion = userService.deleteUser(id);
            if(deletion) {
                var customizedResponse = new CustomizedResponse("Deletion successful", null);
                return new ResponseEntity(customizedResponse, HttpStatus.OK);
            } else {
                var customizedResponse = new CustomizedResponse("Deletion failed", null);
                return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            var customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while deleting from users: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}