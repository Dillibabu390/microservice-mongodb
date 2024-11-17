package com.ncash.ordersservice.entity;

import com.ncash.ordersservice.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private String orderId;
    private String productId;
    private Integer quantity;
    private String productName;
    private Double totalPrice;
    private OrderStatus status;
}
