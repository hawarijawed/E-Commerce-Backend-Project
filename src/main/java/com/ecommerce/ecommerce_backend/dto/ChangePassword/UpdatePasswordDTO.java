package com.ecommerce.ecommerce_backend.dto.ChangePassword;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
