package com.ncash.ordersservice.client;

import com.ncash.ordersservice.dto.ProductDto;
import com.ncash.ordersservice.response.APIOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Product client.
 */
@FeignClient(name = "product-service", url = "${application.config.products-url}")
public interface ProductClient {

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    @GetMapping("/products/{id}")
    ResponseEntity<APIOutput<ProductDto>> getProductById(@PathVariable("id") String id);

    /**
     * Update product response entity.
     *
     * @param id         the id
     * @param productDto the product dto
     * @return the response entity
     */
    @PutMapping("/products/{id}")
    ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto);

    /**
     * Gets all products.
     *
     * @return the all products
     */
    @GetMapping
    ResponseEntity<Object> getAllProducts();
}
