package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByProducts_Id(Long productId);
    void deleteByProductsId(Long productId);
}
