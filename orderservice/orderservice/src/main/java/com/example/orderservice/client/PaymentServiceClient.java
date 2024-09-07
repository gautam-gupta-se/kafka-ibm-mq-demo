package com.example.orderservice.client;
import com.example.orderservice.dto.PaymentRequest;
import com.example.orderservice.dto.PaymentResponse;
import com.example.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;


@Component
public class PaymentServiceClient {

    private final RestTemplate restTemplate;
    private final Environment env;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public PaymentServiceClient(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }


    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 6000))
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
            String paymentServiceUrl = env.getProperty("payment-service.base-url");
        log.info("Client call Attempts: {}",paymentRequest.getOrderId());

            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(paymentServiceUrl, paymentRequest, PaymentResponse.class);
            return response.getBody();

    }

    // Recovery method that matches the method signature and return type
    @Recover
    public PaymentResponse recover(ResourceAccessException e, PaymentRequest paymentRequest) {
        log.error("Fallback executed for order: {} due to: {}", paymentRequest.getOrderId(), e.getMessage());
        // Return a default response indicating failure
        PaymentResponse fallbackResponse = new PaymentResponse();
        fallbackResponse.setOrderId(paymentRequest.getOrderId());
        fallbackResponse.setStatus("FAILED");
        fallbackResponse.setMessage("Payment service is unavailable. Please try again later.");

        return fallbackResponse;
    }
}
