package com.example.paymentservice.utility;

import com.example.paymentservice.dto.PaymentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import java.util.Map;

public class CustomPaymentRequestDeserializer implements Deserializer<PaymentRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public PaymentRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.readValue(data, PaymentRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing JSON to PaymentRequest", e);
        }
    }

    @Override
    public void close() {
    }
}

