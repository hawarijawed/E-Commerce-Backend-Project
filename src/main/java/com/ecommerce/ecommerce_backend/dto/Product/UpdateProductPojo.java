package com.ecommerce.ecommerce_backend.dto.Product;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductPojo {
    @Size(max = 30)
    private String name;
    private String description;
    private int price;
    private String brand;
}
