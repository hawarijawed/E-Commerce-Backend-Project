package com.ecommerce.ecommerce_backend.dto;

import com.ecommerce.ecommerce_backend.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;

@JsonPOJOBuilder
@Data
public class UserPojo {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contact;
    private Role role;
}
