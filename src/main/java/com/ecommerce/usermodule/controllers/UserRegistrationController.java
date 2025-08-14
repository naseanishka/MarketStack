package com.ecommerce.usermodule.controllers;

import com.ecommerce.pojo.ResponseDTO;
import com.ecommerce.pojo.UserDTO;
import com.ecommerce.usermodule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserRegistrationController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        ResponseDTO response = new ResponseDTO();
        /* Apply user registration logic */
        boolean isUserRegistered = userService.userRegisterService(userDTO);
        if(isUserRegistered) {
            response.setCode("200");
            response.setMessage("User registered successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setCode("400");
            response.setMessage("Something went wrong while user registration.");
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String password = userService.fetchPassword(userDTO);
        if(password != null && password.equals(userDTO.getPassword())) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login unsuccessful", HttpStatus.BAD_REQUEST);
    }
}


