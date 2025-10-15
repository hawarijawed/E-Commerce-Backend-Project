package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServices userServices;
    public UserController(UserServices userServices){
        this.userServices = userServices;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(Users user){
        Users savedUser = userServices.saveNewUser(user);
        if(savedUser == null){
            return new ResponseEntity<>("User Not Saved", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public List<Users> getAllUsers(){
        return userServices.getAll();
    }
}
