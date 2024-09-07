package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentServiceController {

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {

        System.out.println("payment request Received and started processing");
        PaymentResponse paymentResponse= new PaymentResponse();
        paymentResponse.setOrderId(paymentRequest.getOrderId());
        if (Math.random() > 0.5) {
            System.out.println("payment request is success");
            paymentResponse.setMessage("Payment is success for the order: "+paymentResponse.getOrderId());
            paymentResponse.setStatus("SUCCESS");
            return new ResponseEntity<>(paymentResponse,HttpStatus.OK);
        } else {
            System.out.println("payment request is failed");
            paymentResponse.setStatus("FAILED");
            paymentResponse.setMessage("Payment is failed for the order: "+paymentResponse.getOrderId());
            return new ResponseEntity<>(paymentResponse,HttpStatus.OK);
        }
    }
}

