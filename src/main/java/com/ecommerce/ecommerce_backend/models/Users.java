package com.ecommerce.ecommerce_backend.models;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    private String contact;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = true; // active or banned user
    private Date createdAt = new Date();

    // Relations (will connect later)
    // @OneToMany(mappedBy = "user")
    // private List<Order> orders = new ArrayList<>();
}
