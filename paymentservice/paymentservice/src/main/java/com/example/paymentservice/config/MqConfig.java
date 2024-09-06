package com.example.paymentservice.config;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class MqConfig {


    @Value("${ibm.mq.host}")
    private String host;
    @Value("${ibm.mq.port}")
    private Integer port;
    @Value("${ibm.mq.queue-manager}")
    private String queueManager;
    @Value("${ibm.mq.channel}")
    private String channel;
    @Value("${ibm.mq.username}")
    private String username;
    @Value("${ibm.mq.password}")
    private String password;
    @Value("${ibm.mq.receive-timeout}")
    private long receiveTimeout;

    @Bean
    public ConnectionFactory mqConnectionFactory() throws  JMSException {
        //MQConnectionFactory connectionFactory = new MQConnectionFactory();
        MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();

        connectionFactory.setHostName(host); // e.g., "localhost"
        connectionFactory.setPort(port); // e.g., 1414, replace with actual port
        connectionFactory.setQueueManager(queueManager); // e.g., "QM1"
        connectionFactory.setChannel(channel); // e.g., "SYSTEM.DEF.SVRCONN"

        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        connectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        connectionFactory.setStringProperty(WMQConstants.USERID, username);
        connectionFactory.setStringProperty(WMQConstants.PASSWORD, password);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory mqConnectionFactory) {
        return new JmsTemplate(mqConnectionFactory);
    }
}

