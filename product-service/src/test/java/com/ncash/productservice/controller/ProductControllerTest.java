package com.ncash.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncash.productservice.constant.ResponseMessage;
import com.ncash.productservice.dto.ProductDto;
import com.ncash.productservice.entity.Product;
import com.ncash.productservice.exception.ProductNotFoundException;
import com.ncash.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto validProductDto;
    private static final String PRODUCT_ID = "7f13b293";
    private static final String NON_EXISTENT_PRODUCT_ID = "999";

    @BeforeEach
    public void setUp() {
        validProductDto = new ProductDto();
        validProductDto.setProductId("7f13b493");
        validProductDto.setName("Test Product");
        validProductDto.setDescription("A test product description");
        validProductDto.setPrice(100.0);
        validProductDto.setStock(50);
        validProductDto.setStatus("AVAILABLE");
    }

    @Test
    public void addProduct_Success() throws Exception {
        Product mockProduct = new Product();
        mockProduct.setProductId("7f13b293");
        mockProduct.setName(validProductDto.getName());
        mockProduct.setDescription(validProductDto.getDescription());
        mockProduct.setPrice(validProductDto.getPrice());
        mockProduct.setStock(validProductDto.getStock());
        mockProduct.setStatus(validProductDto.getStatus());

        when(productService.addProduct(validProductDto)).thenReturn(mockProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(validProductDto.getName()))
                .andExpect(jsonPath("$.data.description").value(validProductDto.getDescription()))
                .andExpect(jsonPath("$.msg").value("DATA SAVED SUCCESSFULLY"));

        verify(productService, times(1)).addProduct(any(ProductDto.class));
    }

    @Test
    public void getAllProducts_Success() throws Exception {
        Product product1 = new Product("1", "Product 1", "Description 1", 100.0, 10, "AVAILABLE");
        Product product2 = new Product("2", "Product 2", "Description 2", 150.0, 20, "OUT_OF_STOCK");
        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Product 1"))
                .andExpect(jsonPath("$.data[1].name").value("Product 2"))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.msg").value(""));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getAllProducts_EmptyList() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.msg").value("No Records Found"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getAllProducts_Error() throws Exception {
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.msg").value("Unexpected error"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_Success() throws Exception {
        when(productService.getProductById(PRODUCT_ID)).thenReturn(validProductDto);

        mockMvc.perform(get("/products/{id}", PRODUCT_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.data.productId").value(validProductDto.getProductId()))
                .andExpect(jsonPath("$.data.name").value(validProductDto.getName()))
                .andExpect(jsonPath("$.msg").value(""));

        verify(productService, times(1)).getProductById(PRODUCT_ID);
    }

    @Test
    void getProductById_ProductNotFound() throws Exception {
        when(productService.getProductById(NON_EXISTENT_PRODUCT_ID)).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(get("/products/{id}", NON_EXISTENT_PRODUCT_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.msg").value("Product not found"));

        verify(productService, times(1)).getProductById(NON_EXISTENT_PRODUCT_ID);
    }

    @Test
    void getProductById_InternalServerError() throws Exception {
        when(productService.getProductById(PRODUCT_ID)).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/products/{id}", PRODUCT_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.msg").value(ResponseMessage.INTERNAL_SERVER_ERROR));

        verify(productService, times(1)).getProductById(PRODUCT_ID);
    }

    @Test
    public void updateProduct_ValidRequest_ShouldReturnUpdatedProduct() throws Exception {
        ProductDto requestProductDto = new ProductDto();
        requestProductDto.setProductId("7f13b493");
        requestProductDto.setName("Updated Product");
        requestProductDto.setDescription("Updated product description");
        requestProductDto.setPrice(120.0);
        requestProductDto.setStock(60);
        requestProductDto.setStatus("AVAILABLE");

        when(productService.updateProduct(eq(PRODUCT_ID), any(ProductDto.class)))
                .thenReturn(requestProductDto);

        mockMvc.perform(put("/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Product"))
                .andExpect(jsonPath("$.data.price").value(120.0))
                .andExpect(jsonPath("$.data.description").value("Updated product description"))
                .andExpect(jsonPath("$.data.stock").value(60))
                .andExpect(jsonPath("$.data.status").value("AVAILABLE"));

        verify(productService, times(1)).updateProduct(eq(PRODUCT_ID), any(ProductDto.class));
    }

    @Test
    public void deleteProduct_Success() throws Exception {
        // Arrange: Mock the service to simulate product deletion
        doNothing().when(productService).deleteProduct(PRODUCT_ID);

        // Act & Assert: Perform DELETE request and expect success
        mockMvc.perform(delete("/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.msg").value(ResponseMessage.DELETED));

        // Verify service method interaction
        verify(productService, times(1)).deleteProduct(PRODUCT_ID);
    }



}
