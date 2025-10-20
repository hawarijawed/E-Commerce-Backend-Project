package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByCategory(String category);
    List<Products> findByPriceGreaterThan(int price);
    List<Products> findByPriceLessThan(int price);
    List<Products> findByPriceBetween(int lowPrice, int highPrice);
}
