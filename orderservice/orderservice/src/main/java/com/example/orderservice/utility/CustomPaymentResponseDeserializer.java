package com.example.orderservice.utility;

import com.example.orderservice.dto.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomPaymentResponseDeserializer implements Deserializer<PaymentResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
		
		// below configure for schema verification for e.g avroschema
		//schemaRegistryUrl = (String) configs.get("schema.registry.url");
		//schemaVersion = (Integer) configs.getOrDefault("schema.version", 1);
    }

    @Override
    public PaymentResponse deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.readValue(data, PaymentResponse.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing JSON to PaymentResponse", e);
        }
    }

    @Override
    public void close() {
    }
}

