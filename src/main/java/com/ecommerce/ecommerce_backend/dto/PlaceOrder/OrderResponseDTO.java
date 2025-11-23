package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderTime;
    private BigDecimal totalAmount;
    private String orderStatus;
    private List<OrderItemResponseDTO> items;
}
