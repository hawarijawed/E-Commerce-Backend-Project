package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.Authentication.LoginDTO;
import com.ecommerce.ecommerce_backend.dto.Authentication.RegisterDTO;
import com.ecommerce.ecommerce_backend.models.Role;
import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import com.ecommerce.ecommerce_backend.utils.JwtUtility;
import io.jsonwebtoken.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtUtility jwtUtility,
                                 PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterDTO dto){
        //Check if mail already exists
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            return "Email already registered!!!";
        }

        Users user = new Users();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setContact(dto.getContact());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() == null? Role.ROLE_USER :dto.getRole());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public String login(LoginDTO dto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        //Get User
        Users user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                ()-> new RuntimeException("User not found")
        );

        return jwtUtility.generateToken(user.getEmail(), user.getRole().name());
    }
}
