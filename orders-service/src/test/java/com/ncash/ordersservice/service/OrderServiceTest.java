package com.ncash.ordersservice.service;

import com.ncash.ordersservice.client.ProductClient;
import com.ncash.ordersservice.constant.OrderStatus;
import com.ncash.ordersservice.dto.OrderRequest;
import com.ncash.ordersservice.dto.ProductDto;
import com.ncash.ordersservice.entity.Order;
import com.ncash.ordersservice.exception.ProductNotFoundException;
import com.ncash.ordersservice.exception.StockInsufficientException;
import com.ncash.ordersservice.repository.OrderRepository;
import com.ncash.ordersservice.response.APIOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private ProductClient productClient; // Mock the ProductClient

    @Mock
    private OrderRepository orderRepository; // Mock the OrderRepository

    @Mock
    private OrderService orderService; // The class under test (OrderService)

    @InjectMocks
    private OrderService orderServiceUnderTest; // Inject mocks into the OrderService instance

    private OrderRequest orderRequest;
    private ProductDto productDto;


    @BeforeEach
    void setUp() {
        // Initialize the mock objects and the class under test
        MockitoAnnotations.openMocks(this);


        // Setup the OrderRequest object for testing
        orderRequest = new OrderRequest();
        orderRequest.setProductId("12345");
        orderRequest.setQuantity(2);

        // Setup a sample ProductDto for testing
        productDto = new ProductDto("12345", "Product A", 54.0, 10, "As", "AVAILABLE");
    }

    @Test
    void placeOrder_success() throws Exception {
        // Arrange: Mock the productClient to return a valid product
        when(productClient.getProductById("12345")).thenReturn(ResponseEntity.ok(new APIOutput<>(true, productDto)));
        when(productClient.updateProduct(eq("12345"), any(ProductDto.class))).thenReturn(ResponseEntity.ok("Product updated"));

        // Act: Call the method
        Order result = (Order) orderServiceUnderTest.placeOrder(orderRequest);

        // Assert: Verify the order status is confirmed, and it calls the repository and message service
        assertNotNull(result);
        assertEquals(OrderStatus.CONFIRMED, result.getStatus());
        assertEquals("Product A", result.getProductName());
        assertEquals(20.0, result.getTotalPrice());
        verify(orderRepository, times(1)).save(result); // Ensure the order is saved
      //  verify(messageService, times(1)); // Ensure message service is called
    }

    @Test
    void placeOrder_productNotFound() {
        // Arrange: Mock productClient to simulate product not found
        when(productClient.getProductById("12345")).thenReturn(ResponseEntity.ok(new APIOutput<>(false, null)));

        // Act: Call the method and capture the response
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            orderServiceUnderTest.placeOrder(orderRequest);
        });

        // Assert: Verify the exception and the error message
        assertEquals("Product not found for ID: 12345", exception.getMessage());
    }

    @Test
    void placeOrder_insufficientStock() {
        // Arrange: Mock productClient to simulate insufficient stock
        productDto.setStock(1); // Only 1 product in stock
        when(productClient.getProductById("12345")).thenReturn(ResponseEntity.ok(new APIOutput<>(true, productDto)));

        // Act: Call the method and capture the response
        Exception exception = assertThrows(StockInsufficientException.class, () -> {
            orderServiceUnderTest.placeOrder(orderRequest);
        });

        // Assert: Verify the exception and the error message
        assertEquals("Not enough stock for product ID: 12345", exception.getMessage());
    }

    @Test
    void placeOrder_productUpdateFailure() {
        // Arrange: Mock the productClient to simulate a product update failure
        when(productClient.getProductById("12345")).thenReturn(ResponseEntity.ok(new APIOutput<>(true, productDto)));
        when(productClient.updateProduct(eq("12345"), any(ProductDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed"));

        // Act: Call the method and capture the response
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderServiceUnderTest.placeOrder(orderRequest);
        });

        // Assert: Verify the exception and the error message
        assertEquals("Failed to update product stock in the Product Microservice", exception.getMessage());
    }

    @Test
    void placeOrder_runtimeException() {
        // Arrange: Mock a runtime exception in the productClient or repository
        when(productClient.getProductById("12345")).thenThrow(new RuntimeException("Unexpected error"));

        // Act: Call the method and capture the response
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderServiceUnderTest.placeOrder(orderRequest);
        });

        // Assert: Verify the exception and the error message
        assertEquals("Unexpected error", exception.getMessage());
    }
}