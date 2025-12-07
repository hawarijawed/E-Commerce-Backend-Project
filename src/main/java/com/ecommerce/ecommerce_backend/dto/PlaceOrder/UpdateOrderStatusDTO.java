package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import com.ecommerce.ecommerce_backend.models.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusDTO {
    private Long orderId;
    private OrderStatus status;
}
