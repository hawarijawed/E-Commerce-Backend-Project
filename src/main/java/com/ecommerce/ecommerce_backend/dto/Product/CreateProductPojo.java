package com.ecommerce.ecommerce_backend.dto.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductPojo {
    @Size(max = 30)
    private String name;
    @NotNull
    private String description;
    @NotNull
    private int price;
    @NotNull
    private String brand;
    private List<String> category;
    @NotNull
    private int stockQuantity;
}
