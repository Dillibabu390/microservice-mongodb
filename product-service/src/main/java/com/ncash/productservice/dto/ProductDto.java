package com.ncash.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {


    private String productId;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    private String description;
    @NotNull
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull
    @Positive(message = "Stock must be positive")
    private Integer stock;

    private String status;
}
