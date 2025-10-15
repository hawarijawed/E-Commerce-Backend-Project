package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
