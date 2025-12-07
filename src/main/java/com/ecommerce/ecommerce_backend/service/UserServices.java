package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.CreateUserPojo;
import com.ecommerce.ecommerce_backend.dto.UpdateUserPojo;
import com.ecommerce.ecommerce_backend.dto.UserPojo;
import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.repository.CartRepository;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
//    public UserServices(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }

    //save user
    public Users saveNewUser(CreateUserPojo userPojo){
        Users user = new Users();
        user.setFirstName(userPojo.getFirstName());
        user.setLastName(userPojo.getLastName());
        user.setEmail(userPojo.getEmail());
        user.setPassword(userPojo.getPassword());
        user.setPassword(userPojo.getPassword());
        user.setContact(userPojo.getContact());
        userRepository.save(user);
        return user;
    }

    //Get all users
    public List<Users> getAll(){
        return userRepository.findAll();
    }

    public Users getById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    //Delete by Id
    @Transactional
    public boolean deleteById(Long id){
        if(userRepository.existsById(id)){
            cartRepository.deleteByUserId(id);
//
            Users users = userRepository.findById(id).orElse(null);
            users.setEnabled(false);
            userRepository.save(users);
            return true;
        }

        return false;
    }

    //Update user
    public Users updateUser(UpdateUserPojo user, Long id){
        Users savedUser = userRepository.findById(id).orElse(null);
        if(savedUser == null){
            return null;
        }

        if(user.getFirstName() != null){
            savedUser.setFirstName(user.getFirstName());
        }

        if(user.getLastName() != null){
            savedUser.setLastName(user.getLastName());
        }

        if(user.getEmail() != null){
            savedUser.setEmail(user.getEmail());
        }

        if(user.getContact() != null){
            savedUser.setContact(user.getContact());
        }

        if(user.getPassword() != null){
            savedUser.setPassword(user.getPassword());
        }

        userRepository.save(savedUser);

        return savedUser;
    }



}
