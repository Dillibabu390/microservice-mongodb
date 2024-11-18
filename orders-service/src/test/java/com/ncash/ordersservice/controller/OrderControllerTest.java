package com.ncash.ordersservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncash.ordersservice.constant.OrderStatus;
import com.ncash.ordersservice.dto.OrderRequest;
import com.ncash.ordersservice.entity.Order;
import com.ncash.ordersservice.exception.ProductNotFoundException;
import com.ncash.ordersservice.exception.StockInsufficientException;
import com.ncash.ordersservice.response.APIOutput;
import com.ncash.ordersservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test order request (mock valid order request)
    private OrderRequest validOrderRequest;

    // Order mock data
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        validOrderRequest = new OrderRequest("productId123", 2);

        mockOrder = new Order("orderId123", "productId123", 2, "Product Name", 200.00, OrderStatus.CONFIRMED);
    }

    @Test
    void placeOrder_SuccessfulOrder() throws Exception {

        APIOutput<Order> orderResponse = new APIOutput<>(true, mockOrder, "Data saved");

        when(orderService.placeOrder(validOrderRequest)).thenReturn(orderResponse);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isOk()) // Expecting 200 OK status
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.msg").value("DATA SAVED SUCCESSFULLY"))
                .andExpect(jsonPath("$.data").exists());

        verify(orderService, times(1)).placeOrder(validOrderRequest);
    }


}
