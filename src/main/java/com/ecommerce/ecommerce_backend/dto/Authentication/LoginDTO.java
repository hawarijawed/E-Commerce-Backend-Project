package com.ecommerce.ecommerce_backend.dto.Authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
