package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.Authentication.LoginDTO;
import com.ecommerce.ecommerce_backend.dto.Authentication.RegisterDTO;
import com.ecommerce.ecommerce_backend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto){
        String res = authenticationService.register(dto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        String token = authenticationService.login(loginDTO);
        return ResponseEntity.ok("Bearer "+token);
    }


}
