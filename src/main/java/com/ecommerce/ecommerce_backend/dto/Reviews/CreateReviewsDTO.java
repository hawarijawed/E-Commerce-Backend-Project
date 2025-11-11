package com.ecommerce.ecommerce_backend.dto.Reviews;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReviewsDTO {
    @NotNull
    private Long productId;
    @NotNull
    private String username;
    @NotNull
    private String comment;
    @Min(1)
    @Max(5)
    private int rating;
}
