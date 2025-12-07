package com.ecommerce.ecommerce_backend.dto.Authentication;

import com.ecommerce.ecommerce_backend.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
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

    private Role role;
}
