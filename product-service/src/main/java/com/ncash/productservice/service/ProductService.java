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
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString().split("-")[0]);
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setStatus(productDto.getStatus());
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
        ProductDto productDTO = new ProductDto();
        productDTO.setProductId(product.getProductId());
        productDTO.setDescription(product.getDescription());
        productDTO.setName(product.getName());
        productDTO.setStock(product.getStock());
        productDTO.setStatus( product.getStatus());
        productDTO.setPrice( product.getPrice());


        return productDTO;
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
        Product updatedProduct = product.get();
        updatedProduct.setName(productDto.getName());
        updatedProduct.setDescription(productDto.getDescription());
        updatedProduct.setPrice(productDto.getPrice());
        updatedProduct.setStock(productDto.getStock());
        updatedProduct.setStatus(productDto.getStatus());
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
