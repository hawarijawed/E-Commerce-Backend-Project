package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrdersId(Long orderId);
}
