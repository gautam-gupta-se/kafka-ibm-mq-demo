package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.payment-response}")
    private String paymentResponseTopic;

    @KafkaListener(topics = "${kafka.topics.payment-request}", groupId = "payment-group")
    public void processPaymentRequest(PaymentRequest request) {
        System.out.println("Processing payment for Order ID: " + request.getOrderId());

        // Simulate payment processing logic
        PaymentResponse response = new PaymentResponse();
        response.setOrderId(request.getOrderId());
        if (processPayment(request.getAmount())) {
            response.setStatus("SUCCESS");
            response.setMessage("Payment completed successfully.");
        } else {
            response.setStatus("FAILED");
            response.setMessage("Payment failed.");
        }

        // Send payment response
        kafkaTemplate.send(paymentResponseTopic, response);
        System.out.println("Payment response sent for Order ID: " + request.getOrderId());
    }

    private boolean processPayment(double amount) {
        // Simulate payment processing logic, return true if successful
        return amount < 1000;  // Let's assume any amount less than 1000 is successful
    }
}

