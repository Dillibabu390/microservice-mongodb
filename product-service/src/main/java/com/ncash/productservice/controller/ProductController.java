package com.ncash.productservice.controller;
import com.ncash.productservice.response.APIOutput;
import com.ncash.productservice.service.ProductService;
import com.ncash.productservice.constant.ResponseMessage;
import com.ncash.productservice.dto.ProductDto;
import com.ncash.productservice.entity.Product;
import com.ncash.productservice.exception.ProductNotFoundException;
import com.ncash.productservice.response.APIResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    /**
     * Add product response entity.
     *
     * @param productDto the product dto
     * @return the response entity
     */
    @Operation(
            description = "addProduct endpoint in Product",
            summary = "save Product data"
    )
    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductDto productDto) {

        log.trace("ProductController details addProduct method invoked !");
        try {
            Product product = productService.addProduct(productDto);
            if (product != null)
                return APIResponseUtil.getResponseWithDataAndMessage(product, ResponseMessage.DATA_SAVED);

            return APIResponseUtil.getResponseForEmptyList();
        }
        catch (Exception e) {
            log.error("Error in ProductController Method addProduct :",e);
            return APIResponseUtil.getResponseWithMessage(e.getMessage());
        }
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    @Operation(
            description = "Get endpoint in Product",
            summary = "Retrieve all data from Products"
    )
    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        log.trace("getAllProducts  get all Products Method invoked ProductController !");
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return APIResponseUtil.getResponseForEmptyList();
            }
            return APIResponseUtil.getResponseWithData(products);
        }catch (Exception e){
            log.error("Error in ProductController Method getAllProducts :",e);
            return  APIResponseUtil.getResponseWithErrorMessageAndErrorCode(e.getMessage());
        }
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    @Operation(
            description = "Get endpoint in by Product Id",
            summary = "Retrieve data by Product Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<APIOutput<ProductDto>> getProductById(@PathVariable String id) {
        log.trace("Get details by Product ID method invoked in ProductController !");
        try {
            // Check for invalid ID
            if (id == null ) {
                return ResponseEntity.badRequest().body(new APIOutput<>(false, null, ResponseMessage.APP_INVALID_PRODUCT_ID));
            }
            // Fetch product details by ID
            ProductDto product = productService.getProductById(id);
            return ResponseEntity.ok(new APIOutput<>(true, product));

        } catch (ProductNotFoundException e) {
            log.error("Unable to fetch product by ID in ProductController Method getProductById: ", e);
            // Handle the case when product is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIOutput<>(false, null, e.getMessage()));
        } catch (Exception e) {
            log.error("Error in ProductController Method getProductById: {}", e.getMessage());
            // Handle general errors and send internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIOutput<>(false, null, ResponseMessage.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * Update product response entity.
     *
     * @param id         the id
     * @param productDto the product dto
     * @return the response entity
     */
    @Operation(
            description = "update endpoint in Product",
            summary = "Update data from Product"
    )
    @PutMapping("/{id}")
 //   @Secured("ADMIN")
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @Valid @RequestBody ProductDto productDto) {

        log.trace("update Product details by ID method invoked in ProductController !");
        try {
            if (id.isEmpty() || id == null) {
                return APIResponseUtil.getResponseWithErrorMessage(ResponseMessage.APP_INVALID_PRODUCT_ID);
            }
            if (productDto != null)
                return APIResponseUtil.getResponseWithData(productService.updateProduct(id, productDto));

            return APIResponseUtil.getResponseForEmptyList();
        } catch (ProductNotFoundException e) {
            log.error("Unable to fetch ID in ProductController Method updateProduct  : ", e);
            return APIResponseUtil.getResponseWithErrorMessage(e.getMessage());
        } catch (Exception e) {
            log.error("Error in ProductController Method updateProduct {}", e);
            return APIResponseUtil.getResponseWithErrorMessage(e.getMessage());
        }
    }


    /**
     * Delete product response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(
            description = "Delete by Id endpoint in Product",
            summary = "Delete Data in Product"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
        log.trace("delete product details by product ID method invoked !");
        try {
            if (id == null) {
                return APIResponseUtil.getResponseWithErrorMessage(ResponseMessage.APP_INVALID_PRODUCT_ID);
            }
            productService.deleteProduct(id);

            return APIResponseUtil.getResponseWithMessage(ResponseMessage.DELETED);
        } catch (ProductNotFoundException e) {
            log.error("Unable to fetch ID in ProductController Method deleteProduct  : ", e);
            return APIResponseUtil.getResponseWithErrorMessage(e.getMessage());
        } catch (Exception e) {
            log.error("Error in ProductController Method deleteProduct {}", e.getMessage());
            return APIResponseUtil.getResponseWithErrorMessage(e.getMessage());
        }
    }









}
