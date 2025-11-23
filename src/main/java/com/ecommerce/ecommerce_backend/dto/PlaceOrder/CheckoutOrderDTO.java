package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckoutOrderDTO {
    @NotBlank
    private String shippingAddress;
    @NotBlank
    private String paymentMethod;
}
