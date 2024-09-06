package com.example.orderservice.intercepter;

import com.example.orderservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class OrderControllerAdvice {
    @ExceptionHandler(JmsException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(JmsException ex) {
        // Log the exception (optional)
        System.out.println("MQ connection issue: " + ex.getMessage());
        ErrorResponse err = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Something messed up with ibm mq",
                HttpStatus.NOT_FOUND.value()
        );

        // Return a custom response with the appropriate HTTP status
        //return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

    }
}
