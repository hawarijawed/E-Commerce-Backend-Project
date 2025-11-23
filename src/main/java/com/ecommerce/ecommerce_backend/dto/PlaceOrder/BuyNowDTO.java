package com.ecommerce.ecommerce_backend.dto.PlaceOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyNowDTO {
    @NotNull
    private Long productId;
    @NotBlank
    private String shippingAddress;
    @NotBlank
    private String paymentMethod;
    @NotNull
    private int quantity;

}
