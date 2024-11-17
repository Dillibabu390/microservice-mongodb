package com.ncash.ordersservice.controller;

import com.ncash.ordersservice.constant.ResponseMessage;
import com.ncash.ordersservice.dto.OrderRequest;
import com.ncash.ordersservice.entity.Order;
import com.ncash.ordersservice.exception.OrderNotFoundException;
import com.ncash.ordersservice.exception.ProductNotFoundException;
import com.ncash.ordersservice.exception.StockInsufficientException;
import com.ncash.ordersservice.response.APIResponseUtil;
import com.ncash.ordersservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Operation(
            description = "Place order endpoint to save order data",
            summary = "save order data order request and send email via apache kafka as consumer " +
                    "in other microservice and poducer in placeorder"
    )
    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.trace("Request to place an order received with product ID: {}", orderRequest.getProductId());

        try {
            // Delegate to service layer for actual business logic
            Object placeOrder = orderService.placeOrder(orderRequest);
            if (placeOrder != null) {
                // If the order was placed successfully, return a success response
                return APIResponseUtil.getResponseWithDataAndMessage(placeOrder, ResponseMessage.DATA_SAVED);
            }
            // If no order is placed (possibly due to stock or validation issues), return an empty response
            return APIResponseUtil.getResponseForEmptyList();
        } catch (ProductNotFoundException e) {
            log.error("Product not found for OrderRequest: {}", orderRequest, e);
            return APIResponseUtil.getResponseWithMessage("Product not found.");

        } catch (StockInsufficientException e) {
            log.error("Insufficient stock for OrderRequest: {}", orderRequest, e);
            return APIResponseUtil.getResponseWithMessage("Insufficient stock for the product.");
        } catch (Exception e) {
            // Catch any other exceptions and log them
            log.error("Unexpected error occurred while placing order: {}", e.getMessage(), e);
            return APIResponseUtil.getResponseWithMessage("An unexpected error occurred. Please try again.");
        }
    }

    @Operation(
            description = "get Order by id",
            summary = "get order by id is use to track the particular order placed"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable String id) {
        log.trace("Get OrderById method invoked in OrderController !");
        try {
            if (id == null) {
                return APIResponseUtil.getResponseWithErrorMessage(ResponseMessage.APP_INVALID_ORDER_ID);
            }
            Order order = orderService.getOrderById(id);
            return APIResponseUtil.getResponseWithData(order);
        } catch (OrderNotFoundException e) {
            log.error("Unable to fetch ID in OrderController Method getOrderById  : ", e);
            return APIResponseUtil.getResponseWithErrorMessage(e.getMessage());
        } catch (Exception e) {
            log.error("Error in OrderController Method getOrderById {}", e.getMessage());
            return APIResponseUtil.getResponseWithDataAndErrorMessage(e.getMessage(),
                    ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
