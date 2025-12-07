package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import lombok.Data;
import org.springframework.aop.target.LazyInitTargetSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailsDTO {
    private Long OrderId;
    private String orderStatus;
    private LocalDateTime orderTime;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String shippingAddress;
    private List<OrderItemsDTO> items;
}
