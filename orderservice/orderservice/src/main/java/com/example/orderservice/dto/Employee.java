package com.example.orderservice.dto;

public record Employee(
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


