package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentSimulationDTO {
    @NotNull
    private Long orderId;
    @NotBlank
    private String status;
}
