package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.models.Orders;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findOrdersByUserIdOrderByOrderTimeDesc(Long userId);
    Optional<Orders> findByIdAndUserId(Long orderId, Long userId);
}
