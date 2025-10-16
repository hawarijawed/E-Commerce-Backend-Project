package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

//    public UserServices(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }

    //save user
    public Users saveNewUser(Users user){
        userRepository.save(user);
        return user;
    }

    //Get all users
    public List<Users> getAll(){
        return userRepository.findAll();
    }

}
