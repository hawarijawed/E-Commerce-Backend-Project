package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.CreateUserPojo;
import com.ecommerce.ecommerce_backend.dto.UpdateUserPojo;
import com.ecommerce.ecommerce_backend.dto.UserPojo;
import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServices userServices;
//    public UserController(UserServices userServices){
//        this.userServices = userServices;
//    }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserPojo user){
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

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Users user = userServices.getById(id);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(@Valid @RequestBody UpdateUserPojo user, @PathVariable Long id){
        Users newUser = userServices.updateUser(user, id);
        if(newUser == null){
            return new ResponseEntity<>("User with mentioned id not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id){
        return userServices.deleteById(id);
    }
}
