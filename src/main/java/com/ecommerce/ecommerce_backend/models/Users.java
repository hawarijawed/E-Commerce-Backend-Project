package com.ecommerce.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
