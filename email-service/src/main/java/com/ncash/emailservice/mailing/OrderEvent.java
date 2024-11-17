package com.ncash.emailservice.mailing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private String orderId;
    private String status;
    private Integer quantity;
}
