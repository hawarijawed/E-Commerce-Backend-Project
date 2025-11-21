package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    Optional<CartItems> findByCartIdAndProductsId(Long cartId, Long productId);
    void deleteCartItemByProductsId(Long productId);
    void deleteByCartId(Long cartId);
}
