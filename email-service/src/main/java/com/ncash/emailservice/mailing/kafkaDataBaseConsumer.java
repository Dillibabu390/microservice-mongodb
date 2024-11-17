package com.ncash.emailservice.mailing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class kafkaDataBaseConsumer {
    private static final String CONFIRMED_STATUS = "CONFIRMED";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmailService emailService;


    @KafkaListener(topics = "orders-topic", groupId = "order-service")
    public void consume(String eventMessage) {
        try {
            log.info("Received event: {}", eventMessage);

            OrderEvent orderEvent = parseOrderEventFromPlainText(eventMessage);

            if (orderEvent == null || orderEvent.getOrderId() == null) {
                log.warn("Received invalid order event: {}", eventMessage);
                return; // Handle invalid order event
            }

            // Build template data based on order status
            Map<String, Object> templateData = buildTemplateData(orderEvent);

            // Check order status and send the appropriate email
            if (CONFIRMED_STATUS.equals(orderEvent.getStatus())) {
                emailService.sendEmail(templateData, true); // For confirmed orders
                log.info("Confirmed order processed: {}", orderEvent.getOrderId());
            } else {
                emailService.sendEmail(templateData, false); // For other order statuses
                log.info("Order failed or status is not confirmed: {}", orderEvent.getOrderId());
            }

        } catch (Exception e) {
            log.error("Unexpected error", e);
        }
    }

    private Map<String, Object> buildTemplateData(OrderEvent orderEvent) {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("orderId", orderEvent.getOrderId());
        templateData.put("status", orderEvent.getStatus());
        templateData.put("current_year", Year.now().getValue());
        return templateData;
    }
    private static OrderEvent parseOrderEventFromPlainText(String eventMessage) {
        OrderEvent orderEvent = new OrderEvent();

        // Simple key-value pair parsing (you might need to adjust this based on your actual format)
        Map<String, String> eventData = new HashMap<>();
        String[] pairs = eventMessage.split(", ");

        for (String pair : pairs) {
            String[] keyValue = pair.split(": ");
            if (keyValue.length == 2) {
                eventData.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        // Set values from the parsed event data
        orderEvent.setOrderId(eventData.get("Order ID"));
        orderEvent.setStatus(eventData.get("Status"));

        return orderEvent;
    }


}
