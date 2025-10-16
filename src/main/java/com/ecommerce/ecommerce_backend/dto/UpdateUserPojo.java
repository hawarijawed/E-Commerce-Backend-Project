package com.ecommerce.ecommerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPojo {

    @Size(max=30, message = "Name length exceeded")
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    @Size(min = 6, message = "Password must be of at least 6 length")
    private String password;
    @Size(min = 10, max = 10)
    private String contact;

}
