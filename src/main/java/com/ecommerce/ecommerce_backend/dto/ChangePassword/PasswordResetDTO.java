package com.ecommerce.ecommerce_backend.dto.ChangePassword;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String token;
    private String newPassword;
}
