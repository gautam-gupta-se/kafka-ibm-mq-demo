package com.example.orderservice.controller;

import com.example.orderservice.dto.PaymentRequest;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private JmsTemplate jmsTemplate;



    @Autowired
    private OrderService orderService;

    @PostMapping("/pay")
    public ResponseEntity<String> createPaymentRequest(@RequestBody PaymentRequest paymentRequest) {
        // Publish the payment request to Kafka
        orderService.submitPayment(paymentRequest.getOrderId(),paymentRequest.getAmount(),paymentRequest.getUserId());
        return ResponseEntity.ok("Payment request submitted successfully for order: " + paymentRequest.getOrderId());
    }
    @GetMapping("/send")
    ResponseEntity<Employee> send() throws JmsException{
        Employee employee2 = new Employee(
                2,
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "987-654-3210",
                "Marketing",
                "Marketing Manager",
                95000.00,
                "456 Elm St, Springfield",
                "2022-03-20"
        );
        jmsTemplate.convertAndSend("DEV.QUEUE.1",employee2);
        return new ResponseEntity<>(employee2, HttpStatus.OK);
        /*try{
            //jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World!");
            //return "Succes";
            jmsTemplate.convertAndSend("DEV.QUEUE.1",employee2);
            return new ResponseEntity<>(employee2, HttpStatus.OK);

        }catch(JmsException ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.OK);
        }*/
    }
    @GetMapping("/hi")
    Employee helloWorld(){

        Employee employee1 = new Employee(
                1,
                "John",
                "Doe",
                "john.doe@example.com",
                "123-456-7890",
                "Engineering",
                "Software Engineer",
                85000.00,
                "123 Main St, Springfield",
                "2023-01-15"
        );
        return employee1;
    }
}

record Employee(
        int id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String department,
        String position,
        double salary,
        String address,
        String hireDate
) {}

