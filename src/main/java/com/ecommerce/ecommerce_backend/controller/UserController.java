package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.ChangePassword.ForgotPasswordDTO;
import com.ecommerce.ecommerce_backend.dto.ChangePassword.PasswordResetDTO;
import com.ecommerce.ecommerce_backend.dto.ChangePassword.UpdatePasswordDTO;
import com.ecommerce.ecommerce_backend.dto.CreateUserPojo;
import com.ecommerce.ecommerce_backend.dto.UpdateUserPojo;
import com.ecommerce.ecommerce_backend.dto.UserPojo;
import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.service.UserServices;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public ResponseEntity<?> updateById(@RequestBody UpdateUserPojo user, @PathVariable Long id){
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

    @DeleteMapping("/delete")
    public boolean deleteAll(){
        return userServices.deleteAll();
    }

    @PutMapping("/password-update")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication from update password: {}",authentication);
        String res = userServices.updatePassword(authentication.getName(), updatePasswordDTO);

        return  ResponseEntity.ok(res);
    }

    @PostMapping("/password-forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO){

        userServices.processForgotPassword(forgotPasswordDTO.getEmail());
        return ResponseEntity.ok("Password reset link sent");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO){

        String res = userServices.resetPassword(passwordResetDTO);

        return ResponseEntity.ok(res);
    }

}
