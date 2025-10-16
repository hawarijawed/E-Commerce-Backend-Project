package com.ecommerce.ecommerce_backend.dto;

import com.ecommerce.ecommerce_backend.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserPojo {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email(message = "Invalid email")
    private String email;

    @Size(max = 10)
    private String contact;
    @Size(min = 6)
    private String password;

    @NotNull
    private Role role;
}
