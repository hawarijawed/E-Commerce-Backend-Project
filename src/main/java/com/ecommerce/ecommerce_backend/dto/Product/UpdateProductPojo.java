package com.ecommerce.ecommerce_backend.dto.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProductPojo {
    @NotNull
    private Long id;
    @Size(max = 30)
    private String name;
    private String description;
    private int price;
    private String brand;
    private List<String> category;
}
