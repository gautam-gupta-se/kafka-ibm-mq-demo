package com.example.orderservice.controller;

import com.example.orderservice.dto.PaymentRequest;
import com.example.orderservice.dto.PaymentResponse;
import com.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderServiceController {

    private final OrderService orderService;

    public OrderServiceController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody PaymentRequest paymentRequest){
            PaymentResponse response = orderService.processOrder(paymentRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
