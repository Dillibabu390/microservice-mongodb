package com.ncash.ordersservice.service;

import com.ncash.ordersservice.client.ProductClient;
import com.ncash.ordersservice.constant.OrderStatus;
import com.ncash.ordersservice.dto.OrderRequest;
import com.ncash.ordersservice.dto.ProductDto;
import com.ncash.ordersservice.entity.Order;
import com.ncash.ordersservice.exception.OrderNotFoundException;
import com.ncash.ordersservice.exception.ProductNotFoundException;
import com.ncash.ordersservice.exception.StockInsufficientException;
import com.ncash.ordersservice.repository.OrderRepository;
import com.ncash.ordersservice.response.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Order service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.orders}")
    private String orderTopic;

    /**
     * Place order object.
     *
     * @param orderRequest the order request
     * @return the object
     * @throws ProductNotFoundException   the product not found exception
     * @throws StockInsufficientException the stock insufficient exception
     */
    @Transactional
    public Object placeOrder(OrderRequest orderRequest) throws ProductNotFoundException, StockInsufficientException {
        // Create a new order object with a FAILED status in case something goes wrong
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString().split("-")[0]);
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());


        try {
            // Fetch product details from Product Microservice
            ProductDto productDto = productClient.getProductById(orderRequest.getProductId()).getBody().getData();

            // Check if the product exists
            if (productDto == null) {
                throw new ProductNotFoundException("Product not found for ID: " + orderRequest.getProductId());
            }

            // Validate stock availability
            if (productDto.getStock() < orderRequest.getQuantity()) {
                throw new StockInsufficientException("Not enough stock for product ID: " + orderRequest.getProductId());
            }

            // Deduct stock
            int finalStock = productDto.getStock() - orderRequest.getQuantity();
            productDto.setStock(finalStock);

            // Update the product stock in Product Microservice
            ResponseEntity<Object> updateResponse = productClient.updateProduct(orderRequest.getProductId(), productDto);
            if (updateResponse.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to update product stock in the Product Microservice");
            }

            // Update the order details if everything goes fine
            order.setStatus(OrderStatus.CONFIRMED);
            order.setProductName(productDto.getName());
            order.setTotalPrice(orderRequest.getQuantity() * productDto.getPrice());
            // Save the order to the database
            orderRepository.save(order);

            // Optionally, send an order message (e.g., to a queue or message broker)
            sendOrderMessage(order);

            // Return the successfully placed order
            return order;

        } catch (RuntimeException ex) {
            // Set the status to FAILED if any exception occurs
            order.setStatus(OrderStatus.FAILED);

            orderRepository.save(order);

            log.error("Order placement failed for Product ID: {} due to: {}", orderRequest.getProductId(), ex.getMessage());

            return ex.getStackTrace();
        }
    }

    /**
     * Gets order by id.
     *
     * @param id the id
     * @return the order by id
     */
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }


    public void sendOrderMessage(Order order) {
        String message = "Order ID: " + order.getOrderId() +
                ",Product Name" + order.getProductName()+
                ", Status: " + order.getStatus()+
                ", Quantity: " + order.getQuantity();
        kafkaTemplate.send(orderTopic, message);  // Send message to Kafka topic
    }


}
