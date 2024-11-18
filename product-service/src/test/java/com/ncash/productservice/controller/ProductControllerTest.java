package com.ncash.productservice.controller;

import com.ncash.productservice.dto.ProductDto;
import com.ncash.productservice.entity.Product;
import com.ncash.productservice.response.APIResponseUtil;
import com.ncash.productservice.service.ProductService;

import com.ncash.productservice.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private APIResponseUtil apiResponseUtil;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        // Creating a ProductDto with values
        ProductDto productDto = new ProductDto(
                "12345", // productId
                "ssss",  // name
                null,    // description (null because you didn't provide it in the example)
                2.3,     // price
                2,       // stock
                ""       // status (empty string as per your example)
        );
        // Creating a Product with values
        Product product = new Product(
                "12345",  // productId
                "ssss",   // name
                null,     // description (null because you didn't provide it in the example)
                2.3,      // price
                2,        // stock
                ""        // status (empty string as per your example)
        );
    }

    @Test
    void addProduct() throws Exception {
        // Arrange: Mock the addProduct method to return a product
        when(productService.addProduct(any(ProductDto.class))).thenReturn(product);

        // Act: Perform the POST request to add a product
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": \"12345\", \"productName\": \"Product A\", \"stock\": 10}")
                )
                .andExpect(status().isOk()) // Assert: Expect HTTP 200 OK
                .andExpect(jsonPath("$.message").value("Data Saved")); // Assert the message in the response
    }

    @Test
    void getAllProducts() throws Exception {
        // Arrange: Mock the getAllProducts method to return a list of products
        when(productService.getAllProducts()).thenReturn(List.of(product));

        // Act: Perform the GET request to get all products
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk()) // Assert: Expect HTTP 200 OK
                .andExpect(jsonPath("$.data.productId").value("12345")); // Assert the product ID in the response
    }

    @Test
    void getProductById() throws Exception {
        // Arrange: Mock the getProductById method to return a productDto
        when(productService.getProductById(anyString())).thenReturn(productDto);

        // Act: Perform the GET request to get a product by ID
        mockMvc.perform(get("/products/12345"))
                .andExpect(status().isOk()) // Assert: Expect HTTP 200 OK
                .andExpect(jsonPath("$.data.productId").value("12345")); // Assert the product ID in the response
    }

    @Test
    void updateProduct() throws Exception {
        // Arrange: Mock the updateProduct method to return an updated productDto
        when(productService.updateProduct(eq("12345"), any(ProductDto.class))).thenReturn(productDto);

        // Act: Perform the PUT request to update a product
        mockMvc.perform(put("/products/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": \"12345\", \"productName\": \"Updated Product\", \"stock\": 20}")
                )
                .andExpect(status().isOk()) // Assert: Expect HTTP 200 OK
                .andExpect(jsonPath("$.data.productName").value("Updated Product")); // Assert the updated product name in the response
    }

    @Test
    void deleteProduct() throws Exception {
        // Arrange: Mock the deleteProduct method to do nothing
        doNothing().when(productService).deleteProduct(anyString());

        // Act: Perform the DELETE request to delete a product
        mockMvc.perform(delete("/products/12345"))
                .andExpect(status().isOk()) // Assert: Expect HTTP 200 OK
                .andExpect(jsonPath("$.message").value("Deleted")); // Assert the response message
    }

    // Exception Tests

    @Test
    void getProductById_NotFound() throws Exception {
        // Arrange: Mock the getProductById method to throw ProductNotFoundException
        when(productService.getProductById(anyString())).thenThrow(new ProductNotFoundException("Product not found"));

        // Act: Perform the GET request to get a product by ID
        mockMvc.perform(get("/products/12345"))
                .andExpect(status().isNotFound()) // Assert: Expect HTTP 404 Not Found
                .andExpect(jsonPath("$.message").value("Product not found")); // Assert the error message
    }

    @Test
    void updateProduct_NotFound() throws Exception {
        // Arrange: Mock the updateProduct method to throw ProductNotFoundException
        when(productService.updateProduct(eq("12345"), any(ProductDto.class)))
                .thenThrow(new ProductNotFoundException("Product not found"));

        // Act: Perform the PUT request to update a product
        mockMvc.perform(put("/products/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": \"12345\", \"productName\": \"Updated Product\", \"stock\": 20}")
                )
                .andExpect(status().isNotFound()) // Assert: Expect HTTP 404 Not Found
                .andExpect(jsonPath("$.message").value("Product not found")); // Assert the error message
    }

    @Test
    void deleteProduct_NotFound() throws Exception {
        // Arrange: Mock the deleteProduct method to throw ProductNotFoundException
        doThrow(new ProductNotFoundException("Product not found")).when(productService).deleteProduct(anyString());

        // Act: Perform the DELETE request to delete a product
        mockMvc.perform(delete("/products/12345"))
                .andExpect(status().isNotFound()) // Assert: Expect HTTP 404 Not Found
                .andExpect(jsonPath("$.message").value("Product not found")); // Assert the error message
    }
}
