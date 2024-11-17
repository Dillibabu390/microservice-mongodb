package com.ncash.productservice.dto;

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

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private String status;
}
