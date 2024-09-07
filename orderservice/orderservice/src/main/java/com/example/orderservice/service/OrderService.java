package com.example.orderservice.service;

import com.example.orderservice.client.PaymentServiceClient;
import com.example.orderservice.dto.PaymentRequest;
import com.example.orderservice.dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private PaymentServiceClient paymentServiceClient;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);


    @Value("${kafka.topics.payment-request}")
    private String paymentRequestTopic;

    public void submitPayment(String orderId, double amount, String userId) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(amount);
        paymentRequest.setUserId(userId);
        kafkaTemplate.send(paymentRequestTopic, paymentRequest);
        System.out.println("Payment request submitted for Order ID: " + orderId);
    }

    @KafkaListener(topics = "${kafka.topics.payment-response}", groupId = "order-group")
    public void handlePaymentResponse(PaymentResponse response) {
        if ("SUCCESS".equals(response.getStatus())) {
            System.out.println("Payment Successful for Order ID: " + response.getOrderId());
            // Update order status in DB
        } else {
            System.out.println("Payment Failed for Order ID: " + response.getOrderId());
        }
    }
    public PaymentResponse processOrder(PaymentRequest paymentRequest) {
        log.info("Processing payment for order: {}", paymentRequest.getOrderId());

        PaymentResponse paymentResponse = paymentServiceClient.processPayment(paymentRequest);

        if (!"SUCCESS".equals(paymentResponse.getStatus())) {
            log.error("Payment failed for order: {} with status: {}", paymentRequest.getOrderId(), paymentResponse.getStatus());
            throw new RuntimeException("Payment failed");
        }

        log.info("Payment succeeded for order: {}", paymentRequest.getOrderId());
        return paymentResponse;  // Returning the response to the caller
    }

}

