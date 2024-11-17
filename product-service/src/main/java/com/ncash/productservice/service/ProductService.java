package com.ncash.productservice.service;

import com.ncash.productservice.constant.ResponseMessage;
import com.ncash.productservice.dto.ProductDto;
import com.ncash.productservice.entity.Product;
import com.ncash.productservice.exception.ProductNotFoundException;
import com.ncash.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Product service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Add product product.
     *
     * @param productDto the product dto
     * @return the product
     */
    public Product addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .productId(UUID.randomUUID().toString().split("-")[0])
                .description(productDto.getDescription())
                .name(productDto.getName())
                .stock(productDto.getStock())
                .price(productDto.getPrice())
                .status(productDto.getStatus())
                .build();
        return productRepository.save(product);
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    @Cacheable(value = "order")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(ResponseMessage.AUD_NO_RECORDS_FOUND));

        // Convert the Product entity to ProductDTO
       return   ProductDto.builder()
                .productId(product.getProductId())
                .description(product.getDescription())
                .name(product.getName())
                .stock(product.getStock())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }

    /**
     * Update product product dto.
     *
     * @param id         the id
     * @param productDto the product dto
     * @return the product dto
     */
    public ProductDto updateProduct(String id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        Product updatedProduct = new Product().builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .status(productDto.getStatus())
                .build();
         productRepository.save(updatedProduct);
        return productDto;
    }

    /**
     * Delete product.
     *
     * @param id the id
     */
    public void deleteProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(ResponseMessage.AUD_NO_RECORDS_FOUND);
        }
        productRepository.deleteById(id);
    }
}
